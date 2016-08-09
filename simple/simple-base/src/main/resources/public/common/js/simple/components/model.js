define(['simple', 'jquery'], function (Simple, $) {
     var Model = {
         "url"    : '',
         "params" : {},
         "extend" : Simple.extend,
         "setParams": function(params){
             this.params = params;
         },
         "setUrl": function(url){
             this.url = url;
         },
         "__onResult" : function (data, textStatus){
             if (textStatus == "success"){
                 var result = this.onResult(data);
             }else{
                 var result = this.onResult(null);
             }
         },
         "add" : function(callback){
             this.onResult = callback;
             $.post(this.url,this.params, _.bind(this.__onResult,this),"json");
         },
         "update" : function(callback){
             this.onResult = callback;
             $.post(this.url,this.params, _.bind(this.__onResult,this),"json");
         },
         "post" : function(url,params,callback){
             $.post(url,params,function (data, textStatus){
                     if (textStatus == "success"){
                         callback(data);
                     }else{
                         callback();
                     }
             }, "json");
         },
         "get": function(url,params,callback){
             $.get(url,params,function (data, textStatus){
                 if (textStatus == "success"){
                     callback(data);
                 }else{
                     callback();
                 }
             }, "json");
         }

     };
     return Model;
});
