/*
*
*/

window.addEventListener('DOMContentLoaded', () =>{
    const likeBtn = document.querySelector('#likeBtn');
    
    if(likeBtn){    
        likeBtn.addEventListener('click', likeMark);
    }
    
    function likeMark(){
        
        const postId = document.querySelector('#id').value;
        const username = document.querySelector('#username').value;
        
        const data = {
            postId : postId,
            username : username };
        
        axios.post('/api/likecount', data)
        .then(response => {
            location.reload();
            console.log(response, data);
        }).catch(err =>{
            console.log(err)
        })
    }
    
    const cancelLikeBtn = document.querySelector('#cancelLikeBtn');
    
    if(cancelLikeBtn){
        cancelLikeBtn.addEventListener('click', cancelLike);
    }
    
    function cancelLike(){
        
        const recruitPostId = document.querySelector('#id').value;
        const username = document.querySelector('#username').value;
            
        axios.delete(`/api/likecount?username=${username}&recruitPostId=${recruitPostId}`)
        .then(response => {
            location.reload();
        }).catch(err => {
            console.log(err)})
    }
    
    
    
})