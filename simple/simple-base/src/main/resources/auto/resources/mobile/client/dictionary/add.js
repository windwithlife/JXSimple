/**
 * Created by zhangyq on 2015/9/15.
 */

define(['simple','text!./templates/add.html','router','homeModel'], function (Simple,tpl,router,homeModel) {

    var page =Simple.PageView.extend({
        //model : new PersonModel(),
        el : '#controller',
        socket: null,
        template: null,
        events: {
            'click .btn-saveAddNew' : 'saveUpdate',
            'click #listBack': 'back',
        },
        render: function(){

        },

        onLoad: function () {
           // var params = {text:"hello list template",title:"list"};
            this.$el.append(tpl);
            this.template = _.template($("#MainTemplate").html());
            var that = this;
            that.$el.html(that.template({}));

             
                 homeModel.queryReferListByName("category",function(data){
                    console.log(JSON.stringify(data));
                    data.forEach(function(selectItem){
                        var item = new Option(selectItem.name, selectItem.id);
                         var obj=document.getElementById("add-category").options.add(item);
                    });
                });
               

        },
        onShow: function () {

        },
        back:function(){
            console.log('list back done');
        },
        saveUpdate: function(){
            //alert("saveUPdate!");
            var params = {};
             
                    params.name = $("#add-name").val();
                

            
                    params.category = {};
                    params.category.id = $("#add-category").val();
                

            

            
             console.log("form data value:" +　JSON.stringify(params));
            homeModel.add(params,function(result){
                 console.log("AddNewSave result:" + JSON.stringify(result));
                if(result){
                    router.goto("");
                }
            });
        }
    });




    return new page();

});
