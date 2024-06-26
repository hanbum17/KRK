/**
 * myreply.js: 댓글/답글 데이터 처리용 Ajax 클로저 모듈  */
 
//alert("댓글 처리 클러저 모듈 소스 임포트.....");

var myCommentModule = (function() {

    function getReplyList(myPaging, callback, error){
        var bno = myPaging.bno ;
        var pageNum = myPaging.pageNum ;
        //console.log("getCmtList()가 전달받은 bno: " + bno) ;
        //console.log("getCmtList()가 전달받은 pageNum: " + pageNum) ;
        
        //댓글 목록 조회 컨트롤러의 매핑 URL: GET /replies/list/{bno}/{pageNum}
        //$.ajax() 함수는, 자바스크립트 객체를 매개값으로 받아 처리  
        $.ajax({
            type: "get" ,
            url: "/mypro00/replies/list/" + bno + "/" + pageNum ,
            dataType: "json" ,
            success: function(myReplyPagingCreator){
                if(callback) {
                    callback(myReplyPagingCreator) ;
                }
            } ,
            error: function(xhr, status, err) {
                if(error) {
                    error(err) ;
                }
            }
        }); //ajax-end
    }
    
    
	function myDateTimeFmt(datetimeValue) {
	    var myDate = new Date(datetimeValue) ;
	    
	    var yyyy = myDate.getFullYear() ;
	    var mm = myDate.getMonth() + 1 ;  // getMonth() is zero-based
	    var dd = myDate.getDate() ;
	    var hh = myDate.getHours() ;
	    var mi = myDate.getMinutes() ;
	    var ss = myDate.getSeconds() ;
	    
	            // 2023/06/02 07:05:03
	    return [yyyy, '/',
	           (mm < 10 ? "0" : "") + mm, "/" ,
	           (dd > 9 ? '' : "0") + dd, " " ,
	           (hh > 9 ? '' : "0") + hh, ":" ,
	           (mi > 9 ? '' : "0") + mi, ":" ,
	           (ss > 9 ? '' : "0") + ss ].join('') ;  //배열값들을 조인시켜 하나의 문자열로 반환
	}

    
    function registerReply(reply, callback){
        var bno = reply.bno ;
        
        //댓글 등록 컨트롤러의 매핑 URL: POST /replies/{bno}/new
        $.ajax({
            type: "post" ,
            url: "/mypro00/replies/" + bno + "/new" ,
            data: JSON.stringify(reply) ,
            contentType: "application/json;charset=utf-8" ,
            dataType: "text" ,
            success: function(result){
                if(callback) {
                    callback(result) ;
                }
            } 
        }); //ajax-end
    }


function modifyReply(reply, callback, error) {
    var bno = reply.bno ;
    var rno = reply.rno ;

    $.ajax({
        type: "put" ,
        url: "/mypro00/replies/" + bno + "/" + rno ,
        data: JSON.stringify(reply) ,
        contentType: "application/json; charset=utf-8" ,
        dataType: "text" ,
        success: function(result, status, xhr) {
            if(callback) {
                callback(result) ;
            }
        } ,
        error: function(xhr, status, err) {
            if(error){
                error(err) ;
            }
        }
    }) ; //ajax-end
}//modifyCmtReply-end


function blindReply(reply, callback, error) {
    var bno = reply.bno ;
    var rno = reply.rno ;

    $.ajax({
        type: "patch" ,
        url: "/mypro00/replies/" + bno + "/" + rno ,
        dataType: "text" ,
        success: function(result, status, xhr) {
            if(callback) {
                callback(result) ;
            }
        } ,
        error: function(xhr, status, err) {
            if(error) {
                error(err) ;
            }
        } 
    });
} //blindReply-end


//특정 댓글-답글 및 자식답글 모두 삭제 요청, 컨트롤러 매핑URL: DELETE:	/replies/{bno}/{rno}
function removeReply(reply, callback, error) {
    var bno = reply.bno ;
    var rno = reply.rno ;

    $.ajax({
        type: "delete" ,
        url: "/mypro00/replies/" + bno + "/" + rno ,
        dataType: "text" ,
        success: function(result, status, xhr) {
            if(callback) {
                callback(result) ;
            }
        } ,
        error: function(xhr, status, err) {
            if(error) {
                error(err) ;
            }
        } 
    });
} //removeReply -end



/*
    function removeAllReply(bno, callback, error) {
        $.ajax({
            type: "delete" ,
            url: "/mypro00/replies/" + bno ,
            dataType: "text" ,
            success: function(result, status, xhr) {
                if(callback) {
                    callback(result) ;
                }
            } ,
            error: function(xhr, status, err) {
                if(error) {
                    error(err) ;
                }
            } 

        });


    } //removeAllReply-end    
    
*/    
    
    //myCommentModule
    return {
       getReplyList: getReplyList ,
       myDateTimeFmt: myDateTimeFmt ,
       registerReply: registerReply ,
       modifyReply: modifyReply ,
       blindReply: blindReply ,
       removeReply: removeReply //추가
    }


})();


/* 
//특정 댓글-답글 조회: 독립적으로 사용되지 않음   
function getReply(bnoAndRno, callback, error) {
    var bno = bnoAndRno.bno ;
    var rno = bnoAndRno.rno ;
    $.ajax({
        type: "get" ,
        url: "/mypro00/replies/" + bno + "/" + rno ,
        dataType: "json" ,
        success: function(reply, status, xhr) {
            if(callback) {
                callback(reply) ;
            }
        } ,
        error: function(xhr, status, err) {
            if(error) {
                error(err) ;
            }
        }
    });//ajax-end
}//getCmtReply-end

*/