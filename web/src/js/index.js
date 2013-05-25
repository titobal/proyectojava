var Model = function(){
    this.setUp = function(){
        $.ajaxSetup({url:"index",type:"POST"});
        this.getProductos();
        this.getComunas();
    };
    this.categorias = new Array();
    this.meta = {
        categoriaSeleccionada : 0
    };
    this.comunas = new Array();
    this.getComunas = function(){
        var me = this;
        $.ajax({data:{m:3}}).done(function(data){
            var fun = function(d){
                me.comunas = new Array();
                for(var x in d){
                    me.comunas.push(d[x]);
                }
                me.getCategorias();
            };
            tools.ajaxDone(data, fun);
        }).fail(tools.ajaxFail);
    };
    this.carro = new Array();
    this.agregaCarro = function(id, stock, muestra){
        var me = this;
        var i = tools.returnIndex(me.carro, function(x) {return x.Id == id;});
        var cant = parseInt(stock);
        if(!isNaN(parseInt(stock))){
            if(typeof i !== "undefined"){
                if(cant <= parseInt(me.productos[tools.returnIndex(me.productos, function(x) {return x.Id == id;})].Stock)){
                    if(cant === 1){
                        me.carro[i].Cantidad++;
                    }
                }else{
                    tools.msg.msg("La cantidad seleccionada no es válida.");
                }
            }else{
                var add = {Id: id, Cantidad: 1};
                me.carro.push(add);
            }
            var cant = 0;
            for(var x in me.carro){
                cant += me.carro[x].Cantidad;
            }
            $("#navCant, #modalCant").html(cant);
            if(muestra){
                me.muestraCarro(false);
            }
        }else{
            tools.msg.msg("La cantidad seleccionada no es válida.");
        }
    };
    this.formConfirmar = function(){
        var me = this;
        total = 0;
        for(var i in me.carro){
            total += me.carro[i].Cantidad * me.productos[tools.returnIndex(me.productos, function(x) {return x.Id == me.carro[i].Id;})].Precio;
        }
        var title = "Procesar compra  <small>"+accounting.formatMoney(total, "$", 2, ".", ",")+"</small>";
        var content = '<div class="row"><div class="span6"><fieldset>'+
                '<div class="control-group"><label class="control-label">Primer Nombre</label><div class="controls">'+
                '<input name="nombre" type="text" required placeholder="Juan" class="input-xlarge"></div></div>'+
                '<div class="control-group"><label class="control-label">Primer Apellido</label><div class="controls">'+
                '<input name="apellidoP" type="text" required placeholder="Perez" class="input-xlarge"></div></div>'+
                '<div class="control-group"><label class="control-label">Segundo Apellido</label><div class="controls">'+
                '<input name="apellidoM" type="text" required placeholder="Pereira" class="input-xlarge"></div></div>'+
                '<div class="control-group"><label class="control-label">Correo Electr&oacute;nico</label><div class="controls">'+
                '<input name="correo" type="email" required placeholder="ejemplo@ejemplo.cl" class="input-xlarge"></div></div>'+
                '</fieldset></div><div class="span5"><fiedlset>'+
                '<div class="control-group"><label class="control-label">Ciudad</label><div class="controls">'+
                '<p>Santaigo</p></div></div>'+
                '<div class="control-group"><label class="control-label">Comuna</label><div class="controls">'+
                '<select name="comuna"></select></div></div>'+
                '<div class="control-group"><label class="control-label">Calle</label><div class="controls">'+
                '<input name="calle" type="text" required placeholder="Arturo Pratt" class="input-xlarge"></div></div>'+
                '<div class="control-group"><label class="control-label">N&uacute;mero</label><div class="controls">'+
                '<input name="numero" type="number" required placeholder="1234" maxlength="6" min="1" max="999999" class="input-xlarge"></div></div>'+
                '</fieldset></div></div>';
        var buttons = '<a onclick="m.muestraCarro();" href="#" data-dismiss="modal" class="btn"><i class="icon-arrow-left"></i> Volver</a>'+
                '<button href="#" type="submit" class="btn btn-primary">Confirmar pedido <i class="icon-white icon-ok"></i></button>';
        $("#modal").html('<div class="modal-header">'+
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
                '<h3>'+title+'</h3></div>'+
            '<form id="fOkCompra" style="margin: 0;" class="form-horizontal"><div class="modal-body">'+content+'</div>'+
            '<div class="modal-footer">'+buttons+'</div></div>');
        var html = "";
        for(var i in me.comunas){
            html+='<option value="'+me.comunas[i].Id+'">'+me.comunas[i].Nombre+'</option>';
        }
        $("#modal select").html(html);
        $("#modal form").submit(function(event){
            (event.preventDefault) ? event.preventDefault() : event.returnValue = false;
            $.ajax({data:{m:4,
                    nombre:$("#modal [name='nombre']").val(),
                    apellidoP:$("#modal [name='apellidoP']").val(),
                    apellidoM:$("#modal [name='apellidoM']").val(),
                    correo:$("#modal [name='correo']").val(),
                    comuna:$("#modal [name='comuna']").val(),
                    calle:$("#modal [name='calle']").val(),
                    numero:$("#modal [name='numero']").val(),
                    carro:JSON.stringify(me.carro)
            }}).done(function(data){
                var fun = function(d){
                    $("#alertModal").html('<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;'+
                            '</button><h3>Correcto</h3></div><div class="modal-body">'+d[0].msg+'</div><div class="modal-footer">'+
                            '<a href="#" data-dismiss="modal" class="btn">Cerrar</a></div>').modal("show");
                    $("#modal").modal("hide");
                };
                tools.ajaxDone(data, fun);
            }).fail(tools.ajaxFail);
        });
    };
    this.muestraCarro = function(){
        var me = this;
        var cant = 0;
        for(var x in me.carro){
            cant += me.carro[x].Cantidad;
        }
        var title = '<img src="src/images/shoping_cart.png"/>  Mi carrito - <span id="modalCant">'+cant+'</span> productos.';
        var buttons = "";
        var content = "";
        if(cant === 0){
            content = "<h1>No tiene productos seleccionados.</h1>";
            buttons = '<a href="#" data-dismiss="modal" class="btn">Cerrar</a>';
            $("#modal").removeClass("container");
        }else{
            content = '<table id="tbProds" class="table table-striped table-bordered table-hover"><thead><tr><th>Producto</th><th>Valor Unitario'+
                    '</th><th>Cantidad</th><th></th><th>Total</th></tr></thead><tbody>';
            var total = 0;
            for(var i in me.carro){
                var p = me.productos[tools.returnIndex(me.productos, function(x) {return x.Id == me.carro[i].Id;})];
                content+='<tr><td>'+p.Nombre+'</td><td>'+accounting.formatMoney(p.Precio, "$", 2, ".", ",")+'</td><td><input type="number" min="1" max="'+
                        p.Stock+'" value="'+me.carro[i].Cantidad+'" val="'+p.Id+'"/></td><td><button val="'+p.Id+'" class="btn btn-primary">'+
                        '<i class="icon-white icon-trash"></i></button></td><td><span id="total'+p.Id+'">'+
                        accounting.formatMoney(p.Precio * me.carro[i].Cantidad, "$", 2, ".", ",")+'</span></td></tr>';
                total += p.Precio * me.carro[i].Cantidad;
            }
            content+='<tr><td colspan="4"><h3 class="text-right">TOTAL:</h3></td><td><h3 id="totalCarro">'+
                    accounting.formatMoney(total, "$", 2, ".", ",")+'</h3></td></tr></tbody></table>';
            buttons = '<a href="#" data-dismiss="modal" class="btn">Cerrar</a>'+
                '<a class="btn btn-primary" href="#" onclick="m.formConfirmar();">Solicitar pedido <i class="icon-arrow-right icon-white"></i></a>';
            $("#modal").addClass("container");
        }
        $("#navCant, #modalCant").html(cant);
        me.printModal(title, content, buttons, true);
        $("#tbProds button").click(function(){
            var id = $(this).attr("val");
            var p = me.productos[tools.returnIndex(me.productos, function(x) {return x.Id == id})];
            tools.confirm("¿Desea retirar "+p.Nombre+" de su carrito?");
            $("#confirm .true").unbind("click").bind("click", function(){
                $("#confirm").modal("hide");
                me.carro.splice(tools.returnIndex(me.carro, function(x) {return x.Id == id}),1);
                me.muestraCarro();
            });
        });
        $("#tbProds [type='number']").change(function(){
            var id = $(this).attr("val");
            var newCant = parseInt($(this).val());
            var p = tools.returnIndex(me.productos, function(x) {return x.Id == id;});
            var c = tools.returnIndex(me.carro, function(x) {return x.Id == id;});
            if(!isNaN(newCant) && newCant <= me.productos[p].Stock && newCant >= 1){
                me.carro[c].Cantidad = newCant;
                $("#total"+id).html(accounting.formatMoney(me.productos[p].Precio * me.carro[c].Cantidad, "$", 2, ".", ","));
                var cant = 0;
                var total = 0;
                for(var x in me.carro){
                    cant += me.carro[x].Cantidad;
                    total += me.carro[x].Cantidad * me.productos[tools.returnIndex(me.productos, function(c) {return c.Id == me.carro[x].Id;})].Precio;
                }
                $("#totalCarro").html(accounting.formatMoney(total, "$", 2, ".", ","));
                $("#navCant, #modalCant").html(cant);
            }else{
                tools.msg.msg("La cantidad seleccionada no es válida.");
                $(this).val(me.carro[c].Cantidad);
            }
        });
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
                        '</p></div><p class="text-center"><button onclick="m.agregaCarro('+me.productos[x].Id+
                        ',1,false);" class="btn"><i class="icon-shopping-cart"></i></button></p></div></li>';
            }
        }else{
            for(var x in me.productos){
                if(me.productos[x].Categoria == me.meta.categoriaSeleccionada){
                    html+='<h1>'+me.categorias[tools.returnIndex(me.categorias,function(x){return x.Id == me.meta.categoriaSeleccionada;})].Nombre+'</h1>';
                    html+='<li class="span3"><div class="thumbnail" val="'+me.productos[x].Id+'"><div class="caption"><h3>'+me.productos[x].Nombre+
                            '</h3><p>'+me.productos[x].Descripcion+
                        '</p></div><p class="text-center"><button onclick="m.agregaCarro('+me.productos[x].Id+
                        ',1,false);" class="btn"><i class="icon-shopping-cart"></i></button></p></div></li>';
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
        $("#modal").removeClass("container");
        me.printModal(p.Nombre, descripcion, '<a href="#" data-dismiss="modal" class="btn">Cancelar</a>'+
                '<a class="btn btn-primary" href="#" onclick="m.agregaCarro('+p.Id+',1,false);">Agregar al carro <i class="icon-shopping-cart icon-white"></i></a>',
                true);
    };
    this.printModal = function(title, content, buttons, muestra){
        $("#modal").html('<div class="modal-header">'+
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
                '<h3>'+title+'</h3></div>'+
            '<div class="modal-body">'+content+'</div>'+
            '<div class="modal-footer">'+buttons+'</div>');
        if(muestra){
            $("#modal").modal("show");
        }
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
        me.printProductos();
    };
};

var m = new Model();

$(document).ready(function(){
    m.setUp();
}).bind("ajaxSend", tools.ajaxStart).bind("ajaxStop", tools.ajaxFinish);