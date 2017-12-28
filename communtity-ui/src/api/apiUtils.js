import axios from 'axios';
import config from './config'

export default {

  get:function(url,params,callback){
    axios.get(url,{params})
      .then(callback)
      .catch(function (error) {
        console.log(error);
      });
  },

  post:function (url,params,callback) {
    axios.post(url,params,config)
      .then(callback)
      .catch(function (error) {
        console.log(error);
      });
  }

}
