/**
 * reply.js
 * 댓글 관련 Ajax 기능 구현.
 * /post/detail.html 에 포함됨.
 */
window.addEventListener('DOMContentLoaded', () => {
    // HTML의 Document Object들이 모두 로딩 끝난 후에 코드들이 실행될 수 있도록 하기 위해서.
    readAllReplies(); // 포스트 상세 페이지가 로딩된 후 댓글 목록 화면 출력.

    const btnReplyRegister = document.querySelector('#btnReplyRegister'); // btnReplyRegister 버튼을 찾고 이벤트 리스너를 등록.
    btnReplyRegister.addEventListener('click', registerNewReply);

    function replyWarning(seq) {
        Swal.fire({
            text: "댓글을 입력해주세요!",
            icon: 'warning',
            confirmButtonColor: 'red',
            confirmButtonText: '확인'
        }).then((result) => {

        })
    }

    function registerNewReply() {// 댓글 작성 함수
        const postId = document.querySelector('#id').value;// 포스트 글 번호 찾음.
        const writer = document.querySelector('#writer').value;// 댓글 작성자 아이디 찾음.
        const replyText = document.querySelector('#replyText').value;// 댓글 내용 찾음.
        if (writer == '' || replyText == '') {// 댓글 작성자와 내용은 비어있으면 안됨.
            replyWarning();
            return; // 메서드 종료
        }
        const data = { // 댓글 등록 Ajax POST 요청을 보낼 때 서버로 보내는 데이터 작성.
            postId: postId, // 댓글이 달릴 포스트 아이디(번호)
            replyText: replyText, // 댓글 내용
            writer: writer // 댓글 작성자
        }; // JavaScript Object

        // Axios 라이브러리를 사용해서 Ajax POST 요청을 보냄.
        axios.post('/api/freesharePostReply', data) // Ajax Post 요청 보냄.
            .then(response => { // 성공 응답(response)이 도착했을 때 실행할 콜백
                console.log(response);
                successReply();
                clearInputs(); // 댓글 작성자, 내용에 작성된 문자열을 삭제.
                readAllReplies(); // 댓글 목록을 다시 요청, 갱신.
            })
            .catch(error => { // 실패 응답(error)이 도착했을 때 실행할 콜백
                console.log(error);
            });

    }

    function successReply(seq) {
        Swal.fire({
            text: "댓글 등록 성공!",
            icon: 'success',
            confirmButtonColor: 'orange',
            confirmButtonText: '확인'
        }).then((result) => { })
    }

    function clearInputs() {
        document.querySelector('#replyText').value = '';  // 댓글 작성자 아이디는 로그인 사용자 아이디로 자동 완성되기 때문에 지우면 안됨.

    }

    function readAllReplies() {
        const postId = document.querySelector('#id').value; // 댓글이 달려 있는 글 번호

        axios
            .get('/api/freesharePostReply/all/' + postId) // Ajax GET 요청 보냄.
            .then(response => { updateReplyList(response.data) })
            .catch(err => { console.log(err) });
    }

    function updateReplyList(data) {
        // 댓글들의 배열(data)을 HTML 영역에 보일 수 있도록 html 코드 작성.
        const divReplies = document.querySelector('#replies');

        let str = ''; // div 안에 들어갈 HTML 코드
        for (let r of data) {
            str += '<div class="card my-2">'
                + '<div class="card-header">'
                + '<h5>[' + r.writer + '] 님의 댓글.</h5>'
                + '</div>'
                + '<div class="card-body">'
                + '<p style="font-size:155%;">' + r.replyText + '</p>'
                + '<p style="font-size:88%;"> [수정 시간] : ' + r.modifiedTime + '</p>'
                + '</div>';
            // 댓글 작성자 아이디와 로그인 사용자 아이디가 같을 때만 "수정" 보여주기.
            if (r.writer == loginUser) {
                str += '<div class="card-footer">'
                    + `<button type="button" class="btnModifies btn btn-outline-primary" data-rid="${r.replyId}">수정</button>`
                    + '</div>';
            }
            str += '</div>';
        }
        divReplies.innerHTML = str;

        // [수정] 버튼들이 HTML 요소로 만들어지 이후에, [수정] 버튼에 이벤트 리스너를 등록.
        const buttons = document.querySelectorAll('.btnModifies');
        buttons.forEach(btn => {
            btn.addEventListener('click', getReply);
        });
    }

    function getReply(event) {
        //console.log(event.target); // 이벤트가 발생한 타겟 -> 버튼
        const rid = event.target.getAttribute('data-rid');// 클릭된 버튼의 data-rid 속성값을 읽음.

        axios// 해당 댓글 아이디의 댓글 객체를 Ajax GET 방식으로 요청.
            .get('/api/freesharePostReply/' + rid)
            .then(response => { showModal(response.data) })
            .catch(err => { console.log(err) });
    }

    const divModal = document.querySelector('#replyModal');
    const replyModal = new bootstrap.Modal(divModal);                   // 부트스트랩 Modal 객체 생성.
    const modalReplyId = document.querySelector('#modalReplyId');       // 댓글 아이디 input
    const modalReplyText = document.querySelector('#modalReplyText');   // 댓글 내용 textarea
    const modalBtnDelete = document.querySelector('#modalBtnDelete');   // 댓글 삭제 버튼
    const modalBtnUpdate = document.querySelector('#modalBtnUpdate');   // 댓글 수정완료 버튼

    function showModal(reply) { // Modal 댓글 아이디/내용 채우기
        modalReplyId.value = reply.replyId;
        modalReplyText.value = reply.replyText;
        replyModal.show(); // 모달을 화면에 보여주기
    }

    modalBtnDelete.addEventListener('click', deleteReply);
    modalBtnUpdate.addEventListener('click', updateReply);



    function updateReply(event) {
        const replyId = modalReplyId.value; // 수정할 댓글 아이디
        const replyText = modalReplyText.value; // 수정할 댓글 내용
        if (replyText == '') {
            replyWarning();
            return;
        }

        Swal.fire({
            title: '댓글을 수정하시겠습니까???',
            icon: 'info',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '수정완료',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.value) {
                const data = { replyText: replyText }; // Ajax 요청으로 보낼 데이터 객체.
                axios
                    .put('/api/freesharePostReply/' + replyId, data) // Ajax PUT 요청을 전송
                    .then(response => {
                        location.reload();
                        readAllReplies(); // 댓글 목록 갱신
                    }) // 성공 응답 처리
                    .catch(err => { console.log(err) }) // 실패 응답 처리
                    .then(function() { // 성공 또는 실패 처리 후 항상 실행할 코드
                        replyModal.hide();
                    });
            }
        })
    }

    function deleteReply(seq) {
        const replyId = modalReplyId.value; // 삭제할 댓글 아이디

        Swal.fire({
            title: '댓글을 정말 삭제하시겠습니까???',
            text: "삭제하시면 다시 복구시킬 수 없습니다.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '삭제',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.value) {
                axios
                    .delete('/api/freesharePostReply/' + replyId) // Ajax DELETE 요청 전송
                    .then(response => {
                        location.reload();
                        readAllReplies(); // 댓글 목록 갱신
                    })
                    .catch(err => { console.log(err) }) // 실패 응답(HTTP 40x, 50x, ...)
                    .then(function() {// 성공 응답 처리 또는 실패 응답 처리가 끝났을 때 무조건 실행할 문장.
                        replyModal.hide(); // 모달 닫기
                    });
            }
        })
    }
});
