/**
 * Created by zhangyq on 2016/4/12.
 */
define(['model'],function(model){
    var rootPath = '/autoapi/';

    var query = function(cb){
        model.get("/dictionary/queryAll",{},cb);
    };
    var queryReferListByName= function(refer,cb){
        model.get( "/" + refer + "/queryAll",{},cb);
    };

    var queryDictionaryListByParams= function(refer, params, cb){
        model.get( "/dictionary/queryByCategory/",params,cb);
    };
    var queryByParams = function(params, cb){
        model.get(rootPath + "dictionarys/query",params,cb);
    };
    var queryById  = function(params,cb){
        model.get("/dictionary/query/"+params.id, params, cb);
    };
    var update = function(params,cb){
        model.post("/dictionary/update/"+params.id,params,cb);
    };
    var remove = function(params,cb){
        model.post("/dictionary/remove/"+params.id,params,cb);
    };
    var add = function(params,cb){
        model.post("/dictionary/save",params,cb);
    };
    return{
        query:query,
        queryById:queryById,
        queryByParams:queryByParams,
        queryReferListByName:queryReferListByName,
        queryReferListByParams:queryDictionaryListByParams,
        queryDictionaryByCategory:queryDictionaryListByParams,
        update:update,
        remove:remove,
        add:add,
         };
});