const config = require("./config");
const path = require("path");
const fs = require("fs");
const express = require("express");
const app = express();
const axios = require("axios");

app.use(express.json());
app.use("/", express.static(path.join(__dirname, "./public")));
app.use(require("cors")());
app.use(require("compression")());

const httpServer = require("http").createServer(app);
const SocketIOServer = require("socket.io");
const { instrument } = require("@socket.io/admin-ui");
const io = SocketIOServer(httpServer, {
    cors: {
        origin: "*",
        credentials: false,
    },
});
instrument(io, {
    auth: false,
});
app.get("/admin", (req, res) => {
    fs.readFile("./public/index.html", (err, data) => {
        res.write(data);
        res.end();
    });
});

const checkCookieToken = function (userId, cookieToken, clientId) {
    return true;
};

function checkServerToken(serverToken) {
    return true;
}

let clientIdToUserId = {};
let userIdToClient = {}; // user-${userId}
io.on("connection", (socket) => {
    console.log(new Date(), "client connected: ", `${io.engine.clientsCount}`);
    socket.join("unauthed");

    socket.on("disconnect", () => {
        console.log(new Date(), "client disconnect: ", `${io.engine.clientsCount}`);
        if (Object.keys(clientIdToUserId).includes(socket.id)) {
            let userId = clientIdToUserId[socket.id];
            let key = `user-${userId}`;
            if (Object.keys(userIdToClient).includes(key)) {
                delete userIdToClient[key];
            }
            delete clientIdToUserId[socket.id];
        }
    });
    socket.on("register", (data) => {
        // data = JSON.parse(data);
        let cookieToken = data.cookieToken || null;
        let userId = data.userId || null;
        if (cookieToken && userId) {
            if (checkCookieToken(userId, cookieToken, socket.id)) {
                console.log("is client");
                // authed
                userIdToClient[`user-${userId}`] = { userId, socket, societId: socket.id, cookieToken };
                clientIdToUserId[socket.id] = userId;
                socket.leave("unauthed");
                // 以 society 为单位创建 room
                // society-admin-${societyId}
                // join-request leave-request (level>100  == 111 for_society-admin)
                // activity-join-request (level>100 == 111 for_society-admin)

                // society-member-${societyId}
                // chat-inner-new-message 为其中一个事件 (level>10 == 11 for_society-member)
                // request-deal-done (level>10 == 11 for_society-member)
                // post-created (level>10 == 11 for_society or for_society-watcher)

                // society-watcher-${societyId}
                // post-created

                axios.post(
                    'http://127.0.0.1:5100/user/room/info',
                    { userId }
                ).then(res => {
                    if (res.status != 200) {
                        console.log(`状态码: ${res.status}`);
                        return;
                    }
                    let userRoomInfo = res.data.data;

                    let societyJoint = userRoomInfo.societyJoint || [];
                    let bbsWatchList = userRoomInfo.bbsUserWatch || [];
                    societyJoint.forEach(sj => {
                        let level = sj.permissionLevel;
                        let societyId = sj.societyId;
                        if (level > 10) { // 11 member
                            socket.join(`society-member-${societyId}`)
                        }
                        if (level > 100) { // 111 admin
                            socket.join(`society-admin-${societyId}`)
                        }
                    });
                    bbsWatchList.forEach(bw => {
                        let societyId = bw.societyId;
                        socket.join(`society-watcher-${societyId}`)
                    });
                }).catch(error => {
                    console.error(error)
                });


                socket.emit("register-result", "authed");
            } else {
                socket.emit("register-result", "not authed");
                // not authed
            }
        } else {
            let serverToken = data.serverToken || null;
            if (checkServerToken(serverToken)) {
                // this is server
                console.log("is server");
                socket.leave("unauthed");
                socket.join("server");

                // server emit this after user -- login(pusher-manually)、joinSociety、watchBBS
                // society-member shouldn't watch its bbs
                socket.on("user-room-info", (d) => {
                    let userId = d.userId;
                    let societyJoint = d.societyJointList || [];
                    let bbsWatchList = d.bbsWatchList || [];
                    let userKey = `user-${userId}`;
                    if (Object.keys(userIdToClient).includes(userKey)) {
                        console.log("user is connected", userId);
                        let socket = userIdToClient[userKey].socket;
                        societyJoint.forEach(uj => {
                            let level = uj.permissionLevel;
                            let societyId = uj.societyId;
                            if (level > 10) { // 11 ord-member
                                socket.join(`society-member-${societyId}`)
                            }
                            if (level > 100) { // 111 admin-member
                                socket.join(`society-admin-${societyId}`)
                            }
                        });
                        bbsWatchList.forEach(bw => {
                            let societyId = bw.societyId;
                            socket.join(`society-watcher-${societyId}`);
                        })
                    } else {
                        console.log("user not connected", userId);
                    }
                });

                socket.on("push-brd", (d) => {
                    // console.log("push-brd", d);
                    let room = d.room;
                    let event = d.event;
                    let data = d.data; // self-designed data-struct
                    io.of("/").to(room).emit(event, data);
                });

                socket.on("push-srd", (d) => {
                    // console.log("push-srd", d);
                    let userId = d.userId;
                    let event = d.event;
                    let data = d.data; // self-designed data-struct
                    let key = `user-${userId}`;
                    if (Object.keys(userIdToClient).includes(key)) {
                        userIdToClient[key].socket.emit(event, data);
                    }
                })
            } else {
                socket.emit("register-result", "data error");
            }
            // data error
        }
    });
});

app.get("/online-count", (req, res, next) => {
    let oc = io.engine.clientsCount;
    res.json({
        status: 200,
        message: "OK",
        data: {
            online_count: oc.length,
        },
    });
});

// should check auth token
// app.post("/push/brd", (req, res, next) => {
//     let room = req.body.room;
//     let event = req.body.event;
//     let data = req.body.data; // self-designed data-struct

// });

// app.post("/push/srd", (req, res, next) => {
//     let userId = req.body.userId;
//     let event = req.body.event;
//     let data = req.body.data; // self-designed data-struct

// })

app.post(
    "notify",
    (req, res, next) => {
        if (req.headers.mainServerAuthToken != config.mainServerAuthToken) {
            res.status(403).json({
                status: 403,
                message: "unauthed",
                data: {},
            });
        } else {
            next();
        }
    },
    (req, res) => {
        if (Object.keys(clientList).includes(req.body.clientId)) {
            clientList[req.body.clientId].socket.emit("notify", req.body.notify);
            res.json({
                status: 200,
                message: "OK",
                data: {},
            });
            res.end();
        } else {
            res.status(404).json({
                status: 404,
                message: "Not Found",
                data: {},
            });
        }
    }
);

httpServer.listen(config.port, (err) => {
    if (err) {
        console.log(err);
    }
    config.hosts.forEach((host) => {
        console.log(`http://${host}:${config.port}`);
    });
});