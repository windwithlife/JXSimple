/**
 * Created by zhangyq on 2016/4/12.
 */
define(['model'],function(model){
    var rootPath = '/autoapi/';

    var query = function(cb){
        model.get(rootPath + "products/",{},function(data){
            cb(data._embedded.products);
        });
    };
    var queryByParams = function(params, cb){
        model.get(rootPath + "products/query",params,cb);
    };
    var queryById  = function(params,cb){
        model.get(rootPath +  "products/"+params.id,params, cb);
    };
    var update = function(params,cb){
        model.post("/product/update/"+params.id,params,cb);
    };
    var remove = function(params,cb){
        model.post("/product/remove/"+params.id,params,cb);
    };
    var add = function(params,cb){
        model.post(rootPath + "products/",params,cb);
    };
    return{
        query:query,
        queryById:queryById,
        queryByParams:queryByParams,
        update:update,
        remove:remove,
        add:add,
         };
});