var Model = function(){
    this.setUp = function(){
        $.ajaxSetup({url:"index",type:"POST"});
        this.getProductos();
    };
    this.categorias = new Array();
    this.meta = {
        categoriaSeleccionada : 0
    };
    this.productos = new Array();
    this.printProductos = function(){
        var me = this;
        var html = "";
        if(typeof tools.returnIndex(me.productos, function(x) {return x.Categoria == me.meta.categoriaSeleccionada;}) === "undefined"){
            html += "<h1>No se han encontrado productos para la categor&iacute;a seleccionada</h1>"+
                    "<h3>Pero te recomendamos los siguientes:</h3>";
            var l = me.productos.length;
            for(var x = 0; x < 10 && x < l; x++){
                html+='<li class="span3"><div class="thumbnail" val="'+me.productos[x].Id+'"><div class="caption"><h3>'+me.productos[x].Nombre+'</h3><p>'+me.productos[x].Descripcion+
                        '</p></div><p class="text-center"><button class="btn"><i class="icon-shopping-cart"></i></button></p></div></li>';
            }
        }else{
            for(var x in me.productos){
                if(me.productos[x].Categoria == me.meta.categoriaSeleccionada){
                    html+='<h1>'+me.categorias[tools.returnIndex(me.categorias,function(x){return x.Id == me.meta.categoriaSeleccionada;})].Nombre+'</h1>';
                    html+='<li class="span3"><div class="thumbnail" val="'+me.productos[x].Id+'"><div class="caption"><h3>'+me.productos[x].Nombre+
                            '</h3><p>'+me.productos[x].Descripcion+
                        '</p></div><p class="text-center"><button class="btn"><i class="icon-shopping-cart"></i></button></p></div></li>';
                }
            }
        }
        $("#listProds").html(html);
        $("#listProds h1").append("<small>      "+$("#listProds li").length+" prductos</small>");
        $("#listProds .caption").click(function(){
            me.muestraProducto($(this).parent().attr("val"));
        });
    };
    this.muestraProducto = function(id){
        var me = this;
        var p = me.productos[tools.returnIndex(me.productos,function(x){return x.Id == id;})];
        var descripcion = '<small>Stock: '+p.Stock+'</small><p>Valor: '+accounting.formatMoney(p.Precio, "$", 2, ".", ",")+
                '</p><p>'+p.Descripcion+'</p>';
        me.printModal(p.Nombre, descripcion, '<a href="#" data-dismiss="modal" class="btn">Cancelar</a><a class="btn btn-primary" href="#">'+
                'Agregar al carro <i class="icon-shopping-cart icon-white"></i></a>');
    };
    this.printModal = function(title, content, buttons){
        $("#modal").html('<div class="modal-header">'+
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
                '<h3>'+title+'</h3></div>'+
            '<div class="modal-body">'+content+'</div>'+
            '<div class="modal-footer">'+buttons+'</div>');
        $("#modal").modal("show");
    };
    this.getProductos = function(){
        var me = this;
        $.ajax({data:{m:2}}).done(function(data){
            var fun = function(d){
                me.productos = new Array();
                for(var x in d){
                    me.productos.push(d[x]);
                }
                me.getCategorias();
            };
            tools.ajaxDone(data, fun);
        }).fail(tools.ajaxFail);
    };
    this.getCategorias = function(){
        var me = this;
        $.ajax({data:{m:1}}).done(function(data){
            var fun = function(d){
                me.categorias = new Array();
                for(var x in d){
                    me.categorias.push(d[x]);
                }
                me.printCategorias();
            };
            tools.ajaxDone(data, fun);
        }).fail(tools.ajaxFail);
    };
    this.categoriaSeleccionada = function(){
        var me = this;
        var html = "";
        if(me.meta.categoriaSeleccionada === 0){
            for(var i in me.categorias){
                if(me.categorias[i].Categoria === "NULL"){
                    html += '<li><a href="#" val="'+me.categorias[i].Id+'">'+me.categorias[i].Nombre+'</a></li>';
                }
            }
        }else{
            var cat = me.meta.categoriaSeleccionada;
            if(typeof tools.returnIndex(me.categorias, function(x) {return x.Categoria == cat;}) === "undefined"){
                cat = me.categorias[tools.returnIndex(me.categorias, function(x) {return x.Id == cat;})].Categoria;
            }
            for(var i in me.categorias){
                if(me.categorias[i].Categoria == cat){
                    html += '<li><a href="#" val="'+me.categorias[i].Id+'">'+me.categorias[i].Nombre+'</a></li>';
                }
            }
        }
        $("#navLeft").html(html);
        $("#navLeft li a[val='"+me.meta.categoriaSeleccionada+"']").parent().addClass("active");
        $("#navLeft li a").click(function(){
            me.meta.categoriaSeleccionada = $(this).attr("val");
            me.categoriaSeleccionada();
        });
        me.printProductos();
    };
    this.printCategorias = function(){
        var me = this;
        var c = me.categorias.slice();
        $("#categorias").html('<ul class="nav"></ul>');
        for(var i in c){
            if(c[i].Categoria === "NULL"){
                if(tools.returnIndex(c, function(x) {return x.Categoria === c[i].Id;}) !== "undefined"){
                    $("#categorias ul").append('<li class="dropdown" val="'+c[i].Id+'"><a href="#" class="dropdown-toggle" data-toggle="dropdown">'+
                            c[i].Nombre+' <b class="caret"></b></a></li>');
                }else{
                    $("#categorias ul").append('<li val="'+c[i].Id+'"><a href="#">'+c[i].Nombre+'</a></li>');
                }
            }else{
                if(c[tools.returnIndex(c, function(x){return x.Id === c[i].Categoria;})].Categoria === "NULL"){
                    if(!$("#categorias ul li[val='"+c[i].Categoria+"']").has("ul").length){
                        $("#categorias ul li[val='"+c[i].Categoria+"']").append('<ul class="dropdown-menu"></ul>');
                    }
                    $("#categorias ul li[val='"+c[i].Categoria+"'] ul").append('<li val="'+c[i].Id+'"><a href="#">'+c[i].Nombre+'</a></li>');
                }else{
                    if(!$("#categorias li[val='"+c[i].Categoria+"']").has("ul").length){
                        $("#categorias li[val='"+c[i].Categoria+"']").addClass("dropdown-submenu").append('<ul class="dropdown-menu"></ul>');
                    }
                    $("#categorias li[val='"+c[i].Categoria+"'] ul").append('<li val="'+c[i].Id+'"><a href="#">'+c[i].Nombre+'</a></li>');
                }
            }
        }
        me.meta.categoriaSeleccionada = 0;
        me.categoriaSeleccionada();
        $("#categorias a").click(function(){
            me.meta.categoriaSeleccionada = $(this).parent().attr("val");
            me.categoriaSeleccionada();
        });
        //$("#categorias li.dropdown > a").unbind("click");
        me.printProductos();
    };
};

var m = new Model();

$(document).ready(function(){
    m.setUp();
}).bind("ajaxSend", tools.ajaxStart).bind("ajaxStop", tools.ajaxFinish);