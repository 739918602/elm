layui.use('element', function(){
    var element = layui.element;
    element.on('tab(tabSwitch)', function(data){
        console.log(data);
    });
});
