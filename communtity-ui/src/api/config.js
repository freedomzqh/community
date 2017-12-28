import Qs from 'qs'
export default{
  baseURL: 'http://localhost:8080/cmt/api/v1/',
  method: 'POST',

  transformRequest: [
    function(data) {
      //由于使用的form-data传数据所以要格式化
      data = Qs.stringify(data);
      return data;
    }
  ],


  transformResponse: [
    function(data) {
      return data;
    }
  ],

  headers: {
    'Content-Type':'application/x-www-form-urlencoded'
  },


  params: {
  },


  paramsSerializer: function(params) {
    return Qs.stringify(params)
  },


  data: {
  },



  withCredentials: false, // default


  responseType: 'json', // default



}
