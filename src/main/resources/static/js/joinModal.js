/**
 * 
 */
 
window.addEventListener('DOMContentLoaded', () => {
    
    const totalMember = document.querySelector('#totalMember').value;
    const joinMember =document.querySelector('#joinMember').value;
   
   // 신청하기 버튼
   const btnApplyJoin = document.querySelector('#btnApplyJoin');
   
   
   btnApplyJoin.addEventListener('click', joinNewApply);
   
   const divModal = document.querySelector('#staticBackdrop');
   const applyModal = new bootstrap.Modal(divModal);
   
   // 신청 함수
   function joinNewApply(){
    
    // 포스트 글번호 
    const postId = document.querySelector('#id').value;
    // 신청자 아이디
    const joinNickname = document.querySelector('#joinNickname').value;
    
    const data = { postId: postId,
        joinNickname: joinNickname };

    if(totalMember != joinMember) {
        axios.post('/api/apply', data)
        .then(response => {
            console.log(response, data);
            joinAlert();
        })
        .catch(error => {
            console.log(error)
        })
        .then(function(){applyModal.hide()});
    } else {
        alert('모집인원이 완료되어 신청이 불가능합니다.')
    }
}

function joinAlert(seq) {
  Swal.fire({
            text: '신청완료! 신청 내용은 마이페이지에서 확인 가능합니다.',
            showCancelButton: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result)=> {
            // 부모 페이지 로딩 코드
            location.reload();
            countMember();
        }) 
}
    

    // 신청 취소 버튼
    const btnNoJoin = document.querySelector('#btnNoJoin');
    btnNoJoin.addEventListener('click', noJoinMeeting);
    
    function noJoinMeeting(){
        //삭제할 닉네임 아이디
        const joinNickname2 = document.querySelector('#joinNickname').value;
        const recruitPostId = document.querySelector('#id').value;
        
        Swal.fire({
            text: '신청취소 하시겠습니까?',
            showCancelButton: true,
            confirmButtonColor: 'red',
            cancelButtonColor: 'gray',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then((result)=> {
            if(result.value) {
            axios
            .delete(`/api/apply?joinNickname=${joinNickname2}&recruitPostId=${recruitPostId}`)
            .then(response => {
                cancelAlert();
            })
            .catch(err => {console.log(err)})
            }
        }) 
    }

function cancelAlert(seq) {
      Swal.fire({
            text: '신청 취소 완료',
            showCancelButton: false,
            confirmButtonColor: '#3085d6',
            confirmButtonText: '확인'
        }).then((result)=> {
            // 부모 페이지 로딩 코드
            location.reload();
        }) 
}
    
    

});