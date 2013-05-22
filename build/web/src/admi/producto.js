var Producto = function(){
    this.setUp = function(){
        this.getCategorias();
        this.getProductos();
        $("#Productos .bt-update").click(function(){
            prod.getProductos();
            prod.getCategorias();
        });
        $("#Productos .bt-new").click(function(){
            prod.showfNew();
        });
    };
    this.printProductos = function(){
        var me = this;
        var html = "";
        var p = me.productos;
        for(var x in p){
            html+='<li class="span4"><div class="thumbnail"><div class="caption"><h3>'+p[x].Nombre+
                    '</h3><p>'+p[x].Descripcion+'</p></div></div></li>';
        }
        $("#listProds").html(html);
    };
    this.productos = new Array();
    this.categorias = new Array();
    this.meta = {catAct:0,selCat:null};
    this.getProductos = function(){
        var me = this;
        $.ajax({data:{o:2,m:2}}).done(function(data){
            var fun = function(d){
                me.productos = new Array();
                for(var x in d){
                    me.productos.push(d[x]);
                }
                prod.printProductos();
            };
            tools.ajaxDone(data, fun);
        }).fail(tools.ajaxFail);
    };
    this.getCategorias = function(){
        $.ajax({data:{o:1,m:1}}).done(function(data){
            var fun = function(d){
                prod.categorias = new Array();
                for(var x in d){
                    prod.categorias.push(d[x]);
                }
            };
            tools.ajaxDone(data, fun);
        }).fail(tools.ajaxFail);
    };
    this.showfNew = function(){
        var me = this;
        var form = '<fieldset><div class="control-group"><label class="control-label">Nombre</label><div class="controls">'+
                   '<input required name="nombre" type="text" placeholder="Nombre del producto" class="input-xlarge">'+
                   '</div></div><div class="control-group"><label class="control-label">Descripcion</label><div class="controls">'+
                   '<textarea required name="descripcion" placeholder="Nombre del producto" class="input-xlarge"></textarea>'+
                   '</div></div><div class="control-group"><label class="control-label">Precio</label><div class="controls">'+
                   '<input required name="precio" type="number" step="0.1" min="1" placeholder="1000,0" class="input-xlarge">'+
                   '</div></div><div class="control-group"><label class="control-label">Stock</label><div class="controls">'+
                   '<input required name="stock" type="number" step="1" min="1" placeholder="100" class="input-xlarge"></div></div>'+
                   '<div class="control-group"><label class="control-label">Estado</label><div class="controls">'+
                   '<div class="btn-group" data-toggle="buttons-radio"><button type="button" class="btn btn-primary active" value="1">Visible</button>'+
                   '<button type="button" class="btn btn-primary" value="0">Oculto</button></div></div></div>'+
                   '<div class="control-group"><label class="control-label">Categor&iacute;a:<br/><span id="dirCat">SELECCIONE UNA CATEGOR&Iacute;A</span></label>'+
                   '<div style="height:100px;border:1px solid #ccc;border-radius:5px;" id="SelCategoria" class="controls"></div></div></fieldset>';
        var id = tools.randomString(10);
        me.meta.catAct = null;
        me.meta.selAct = null;
        var params = {
            form: form,
            id:id,
            title:"Nuevo Producto"
        };
        $(c.v.i4).html(tools.fTemplate(params)).modal("show").addClass("container").find("form").addClass("form-horizontal").submit(function(event){
            (event.preventDefault) ? event.preventDefault() : event.returnValue = false;
            if(prod.meta.selAct !== null){
                var obj = {o:2,m:1,nombre: $(c.v.i4+" [name='nombre']").val(),
                        descripcion: $(c.v.i4+" [name='descripcion']").val(),
                        precio: $(c.v.i4+" [name='precio']").val(),
                        stock: $(c.v.i4+" [name='stock']").val(),
                        estado: $(c.v.i4+" .btn-group .active").val(),
                        categoria: prod.meta.selAct};
                console.log(obj);
                $.ajax({data:obj}).done(function(data){
                    var fun = function(d){
                        tools.msg.per("Correcto","El producto se ha ingresado correctamente.","success");
                        prod.getProductos();
                    };
                    tools.ajaxDone(data, fun);
                }).fail(tools.ajaxFail);
            }else{
                tools.msg.per("Error","Debe seleccionar una categor&iacute;a","error");
            }
        });
        me.printSelCategorias("NULL");
    };
    this.printSelCategorias = function(id){
        var me = this;
        me.meta.selAct = null;
        $("#dirCat").html("SELECCIONE UNA CATEGOR&Iacute;A");
        var html = "";
        var addButton = function(id, nombre){
            return "<div class='btn-group'><button type='button' val='"+id+"' class='btn into'>"+nombre+"</button>"+
                    "<button type='button' class='btn ok' val='"+id+"'><i class='icon-ok'></i></button></div>&nbsp;&nbsp;";
        };
        if(id !== "NULL"){
            var a = tools.returnIndex(me.categorias, function(x){return x.Id === id;});
            me.meta.catAct = me.categorias[a].Categoria;
            html +="<button type='button' class='btn into' val='"+me.meta.catAct+"'>Volver</button>&nbsp;&nbsp;";
        }
        var cat = me.categorias.slice(0);
        var l = cat.length;
        for(var x = 0; x < l; x++){
            var i = tools.returnIndex(cat, function(x) {return x.Categoria === id;});
            if(typeof i !== "undefined"){
                html+= addButton(cat[i].Id, cat[i].Nombre);
                cat.splice(i,1);
            }
        }
        $("#SelCategoria").html(html);
        $("#SelCategoria .into").click(function(){
            prod.printSelCategorias($(this).attr("val"));
        });
        $("#SelCategoria .ok").click(function(){
            me.meta.selAct = $(this).attr("val");
            $("#dirCat").html(me.categorias[tools.returnIndex(me.categorias, function(x) {return x.Id === prod.meta.selAct;})].Nombre);
        });
    };
};

var prod = new Producto();