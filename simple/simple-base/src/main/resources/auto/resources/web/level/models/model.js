/**
 * Created by zhangyq on 2016/4/12.
 */
define(['model'],function(model){
    var rootPath = '/autoapi/';

    var query = function(cb){
        model.get("/level/queryAll",{},cb);
    };
    var queryReferListByName= function(refer,cb){
        model.get( "/" + refer + "/queryAll",{},cb);
    };

    var queryByNameLike = function(params, cb){
        model.get("/level/queryByNameLike/",params,cb);
    };

    var queryDictionaryListByParams= function(refer, params, cb){
        model.get( "/dictionary/queryByCategory/",params,cb);
    };
    var queryByParams = function(params, cb){
        model.get(rootPath + "levels/query",params,cb);
    };
    var queryById  = function(params,cb){
        model.get("/level/query/"+params.id, params, cb);
    };
    var update = function(params,cb){
        model.post("/level/update/"+params.id,params,cb);
    };
    var remove = function(params,cb){
        model.post("/level/remove/"+params.id,params,cb);
    };
    var add = function(params,cb){
        model.post("/level/save",params,cb);
    };
    return{
        query:query,
        queryById:queryById,
        queryByParams:queryByParams,
        queryByNameLike:queryByNameLike,
        queryReferListByName:queryReferListByName,
        queryReferListByParams:queryDictionaryListByParams,
        queryDictionaryByCategory:queryDictionaryListByParams,
        update:update,
        remove:remove,
        add:add,
         };
});