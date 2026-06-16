// 댓글 수 표시 업데이터
function updateCommentCount(delta){
   const countEl = document.getElementById("commentCount");
   countEl.textContent = parseInt(countEl.textContent) + delta;
}
// 새 댓글 DOM 요소를 댓글 목록에 추가
function appendComment(comment){
   
   const loginMemberId = parseInt(document.getElementById('loginMemberId').value);
   
   const div = document.createElement('div');
   div.className = 'comment-item';
   div.id = 'comment-' + comment.commentId;
   
   // 본인 댓글이면 삭제 버튼 html 추가 
   const deleteBtn = (comment.memberId == loginMemberId)?
      `<button class="btn btn-sm btn-danger" onclick="deleteComment(${comment.commentId})">삭제</button>`
      : '';
   
   // innerHTMl - div안에 html 내용을 설정
   div.innerHTML = 
      '<span class="comment-author">' + comment.memberLoginId + '</span>' +
      '<span class="comment-cotent">' + comment.commentContent + '</span>' +
      '<span class="comment-date">' + comment.commentDate + '</span>' +
      deleteBtn;
   
   // 만든 div를 실제 댓글 목록(commentList)에 추가 -> 화면에 나타남
   document.getElementById("commentList").appendChild(div);
}

// 댓글 등록(AJAX)
// async - 이 함수 안에서 await 를 사용하겠다는 선언
async function submitComment(){
   
   // hidden input에서 게시글 번호와 댓글 내용을 읽어옴
   const boardId = document.getElementById('boardId').value;
   const contentInput = document.getElementById('commentContent');
   const content = contentInput.value.trim(); // trim() : 앞뒤 공백 제거
   
   if(!content){
      alert("댓글 내용을 입력해주세요!");
      return;
   }
   
   try{
      const response = await fetch("/comment/insert", {
         method : 'POST',
         headers:{'Content-Type':'application/json'},
         body: JSON.stringify({
            boardId: parseInt(boardId), // 숫자형으로 변환
            commentContent: content
         })
      });
      
      const result = await response.json();
      console.log(result);
      
      if(result.success){
         appendComment(result.comment); 
         contentInput.value = ''; 
         updateCommentCount(1);  
      }else{
         alert(result.message || '댓글 등록에 실패했습니다.');
      }
      
   } catch(error){
      // 네트워크 오류 등 예외 상황 처리
      alert("오류가 발생했습니다. 다시 시도해주세요.");
      console.error(error); 
   }
}

// 댓글 삭제(AJAX)
// commentId - 삭제할 댓슬 번호(detail.html에서 onclick으로 전달)
async function deleteComment(commentId){
   if(!confirm("댓글을 삭제하시겠습니까?")){
      return;
   }
   try{
      const response = await fetch("/comment/delete/"+commentId,{
         method: 'POST'
      });
      
      const result = await response.json();
      
      if(result.success){
         // id = comment- {commentId}로 설정된 DOM요소 찾아서 제거
         document.getElementById('comment-'+commentId).remove();
         updateCommentCount(-1); // 댓글 수 -1
      } else{
          alert("댓글 삭제에 실패했습니다.");
      }
      
      
   }catch(error){
      alert("오류가 발생했습니다. 다시 시도해주세요.");
      console.error(error);
   }
   
   
}


















