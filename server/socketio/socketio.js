const io = require("socket.io-client");
const { pusherUrl } = require("../config.json");
let socket = io(pusherUrl);

socket.on("connect", () => {
    console.log("connect");
    socket.emit("register", {
        deviceName: "sbw-server-main-nodejs-express",
        token: "asdfghjkl",
        authPass: "qazwsx",
        deviceType: "server",
        character: "main-server",
        serverToken: "wsx"
    });
});

socket.on("data", (data) => {
    console.log(data);
});

socket.on("connect-error", () => {
    console.log("connect-error");
});

const emitUserRoomInfo = (userId, societyJointList, bbsWatchList) => {
    socket.emit("user-room-info", { userId, societyJointList, bbsWatchList });
};

const emitPushBrd = (room, event, data) => {
    socket.emit("push-brd", {
        room, event, data
    });
};

const emitPushSrd = (userId, event, data) => {
    socket.emit("push-srd", { userId, event, data });
};

const brdChatInner = (societyId, societyName, message, userId, username) => {
    emitPushBrd(
        `society-member-${societyId}`,
        "society-chat-inner",
        { societyId, societyName, message, userId, username }
    );
};

const brdNewPost = (societyId, bbsName, postTitle, userId) => {
    emitPushBrd(
        `society-member-${societyId}`,
        "bbs-new-post",
        { societyId, bbsName, postTitle, userId }
    );
};

const brdNewMemberRequest = (societyId, societyName, userId, username) => {
    emitPushBrd(
        `society-admin-${societyId}`,
        "society-new-member-request",
        { societyId, societyName, userId, username }
    )
};

const brdNewActivityRequest = (societyId, societyName, userId, username) => {
    emitPushBrd(
        `society-admin-${societyId}`,
        "society-new-activity-request",
        { societyId, societyName, userId, username }
    )
}

const brdMemberChanged = (societyId, societyName) => {
    emitPushBrd(
        `society-member-${societyId}`,
        "society-member-changed",
        { societyId, societyName }
    )
};

const brdNewNoticeMember = (societyId, societyName, postUserId, postUsername, title) => {
    emitPushBrd(
        `society-member-${societyId}`,
        "society-new-notice",
        { societyName, postUserId, postUsername, title }
    )
};
const brdNewNoticeWatcher = (societyId, societyName, postUserId, postUsername, title) => {
    emitPushBrd(
        `society-watcher-${societyId}`,
        "society-new-notice",
        { societyName, postUserId, postUsername, title }
    )
};

const brdNewNoticeAdmin = (societyId, societyName, postUserId, postUsername, title) => {
    emitPushBrd(
        `society-admin-${societyId}`,
        "society-new-notice",
        { societyName, postUserId, postUsername, title }
    )
};

const srdNewPostReply = (postUserId, replyUserId, replyUsername, societyId, bbsName, postTitle, reply) => {
    emitPushSrd(
        postUserId,
        "bbs-new-post-reply",
        { replyUserId, replyUsername, societyId, bbsName, postTitle, reply }
    );
};

const srdMemberRequestReply = (userId, societyId, societyName) => {
    emitPushSrd(
        userId,
        "society-member-request-reply",
        { societyId, mesocietyNamessage }
    );
};

const srdChatPrivate = (recvUserId, username, userId, message) => {
    emitPushSrd(
        recvUserId,
        "user-chat-private",
        { userId, username, message }
    );
};

module.exports = {
    socket: socket,

    emitUserRoomInfo,
    emitPushBrd,
    emitPushSrd,
    brdChatInner,
    brdNewPost,
    brdMemberChanged,
    brdNewMemberRequest,
    brdNewActivityRequest,
    brdNewNoticeMember,
    brdNewNoticeWatcher,
    brdNewNoticeAdmin,
    srdNewPostReply,
    srdMemberRequestReply,
    srdChatPrivate
};