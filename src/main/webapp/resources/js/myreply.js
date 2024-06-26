var myCommentModule = (function () {
    function getReplyList(myPaging, callback, error) {
        var bno = myPaging.bno;
        var pageNum = myPaging.pageNum;

        $.ajax({
            type: "GET",
            url: "/mypro00/replies/list/" + bno + "/" + pageNum,
            dataType: "json",
            success: function (myReplyPagingCreator) {
                if (callback) {
                    console.log("서버처리 정상");
                    callback(myReplyPagingCreator);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }
    
    function myDateTimeFmt(datetimeValue) {
        var myDate = new Date(datetimeValue);
        
        var yyyy = myDate.getFullYear();
        var mm = myDate.getMonth() + 1;        
        var dd = myDate.getDate();
        var hh = myDate.getHours();
        var mi = myDate.getMinutes();
        var ss = myDate.getSeconds();

        return [yyyy, '/',
                (mm > 9 ? '' : "0") + mm, "/",
                (dd > 9 ? '' : "0") + dd, " ",
                (hh > 9 ? '' : "0") + hh, ":",
                (mi > 9 ? '' : "0") + mi, ":",
                (ss > 9 ? '' : "0") + ss].join('');
    }
    
    function registerComment(comment, callback, error) {
        var bno = comment.bno;

        $.ajax({
            type: "POST",
            url: "/mypro00/replies/" + bno + "/new",
            data: JSON.stringify(comment),
            contentType: "application/json;charset=utf-8",
            dataType: "text",
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    function registerReply(reply, callback, error) {
        var bno = reply.bno;
        var prno = reply.prno;

        $.ajax({
            type: "POST",
            url: "/mypro00/replies/" + bno + "/" + prno + "/new",
            data: JSON.stringify(reply),
            contentType: "application/json;charset=utf-8",
            dataType: "text",
            success: function (result) {
                if (callback) {
                    console.log("서버처리 정상");
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    function updateComment(comment, callback, error) {
        var bno = comment.bno;
        var rno = comment.rno;

        $.ajax({
            type: "PUT",
            url: "/mypro00/replies/" + bno + "/" + rno,
            data: JSON.stringify(comment),
            contentType: "application/json;charset=utf-8",
            dataType: "text",
            success: function (result) {
                if (callback) {
                    console.log("서버처리 정상");
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    function deleteComment(bno, rno, callback, error) {
        $.ajax({
            type: "DELETE",
            url: "/mypro00/replies/" + bno + "/" + rno,
            dataType: "text",
            success: function (result) {
                if (callback) {
                    console.log("서버처리 정상");
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    function blindComment(bno, rno, callback, error) {
        $.ajax({
            type: "PATCH",
            url: "/mypro00/replies/" + bno + "/" + rno,
            dataType: "text",
            success: function (result) {
                if (callback) {
                    console.log("서버처리 정상");
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }

    return {
        getReplyList: getReplyList,
        registerComment: registerComment,
        registerReply: registerReply,
        updateComment: updateComment,
        deleteComment: deleteComment,
        blindComment: blindComment,
        myDateTimeFmt: myDateTimeFmt
    };
})();
