const userName = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
const password =/^[a-zA-Z0-9]{6,20}$/;
const nickName=/^[\u4e00-\u9fa5]{3,8}$/
export default {
  userName,password,nickName
}
