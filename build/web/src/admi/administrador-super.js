var Administrador = function(){
    this.administradores = new Array();
    this.adapta = function(){
        $("#Administradores table thead tr").append("<th></th>")
    };
    this.table = _.template("<tr val='<%= Correo %>'><td><%= Correo %></td><td><button class='btn btn-primary btn-mini nivel'><%= Nivel == 0? 'Super Administrador' : 'Administrador' %></button></td><td><%= UltimaSesion == 'NULL' ? 'No ha iniciado sesión' : tools.printDate(UltimaSesion) %></td><td><button class='btn btn-primary btn-mini estado'><%= Estado == '1'?'Activo':'Inactivo' %></button></td><td><button class='btn btn-primary btn-mini elimina'><i class='icon-white icon-trash'></i></button></td></tr>");
    this.printTable = function(){
        var me = this;
        var html = "";
        var a = me.administradores;
        for(var x in a){
            html += me.table(a[x]);
        }
        $("#Administradores table tbody").html(html);
        $("#Administradores table tbody tr button.nivel").click(function(){
            me.cambiaNivel($(this).closest("tr").attr("val"));
        });
        $("#Administradores table tbody tr button.estado").click(function(){
            me.cambiaEstado($(this).closest("tr").attr("val"));
        });
        $("#Administradores table tbody tr button.elimina").click(function(){
            me.elimina($(this).closest("tr").attr("val"));
        });
    };
    this.includeAdmin = function(d){
        var me = this;
        var i = tools.returnIndex(me.administradores, function(x) {return x.Correo === d.Correo;});
        if(i !== "undefined"){
            me.administradores[i] = d;
        }
        else{
            me.administradores.push(d);
        }
        me.printTable();
    };
    this.removeAdmin = function(d){
        var me = this;
        var i = tools.returnIndex(me.administradores, function(x) {return x.Correo === d.Correo;});
        if(i !== "undefined"){
            me.administradores.splice(i,1);
        }
        else{
            tools.msg.errIne();
        }
        me.printTable();
    };
    this.getAdministradores = function(){
        var me = this;
        $.ajax({data:{o:3,m:1}}).done(function(data){
            var fun = function(d){
                me.administradores = new Array();
                for(var x in d){
                    me.administradores.push(d[x]);
                }
                me.printTable();
            };
            tools.ajaxDone(data, fun);
        }).fail(tools.ajaxFail);
    };
    this.fNuevoAdmin = function(){
        var me = this;
        var form = '<fieldset><div class="control-group"><label class="control-label">Correo Electrónico</label><div class="controls">'+
                '<input required name="correo" type="email" placeholder="ejemplo@ejemplo.cl" class="input-xlarge"></div></div>'+
                '<div class="control-group"><label class="control-label">Nivel</label><div class="controls">'+
                '<div class="btn-group" data-toggle="buttons-radio"><button type="button" class="btn btn-primary active" value="1">Administrador</button>'+
                '<button type="button" class="btn btn-primary" value="0">Super Administrador</button></div></div></div>'+
                '<p class="text-center">Estado: Activo</p></fieldset>';
        var id = tools.randomString(10);
        var params = {
            form: form,
            id:id,
            title:"Nuevo Administrador"
        };
        $(c.v.i4).html(tools.fTemplate(params)).modal("show").removeClass("container").find("form").addClass("form-horizontal").submit(function(event){
            (event.preventDefault) ? event.preventDefault() : event.returnValue = false;
            $.ajax({data:{o:3,m:2,
                    correo:$(c.v.i4 + " [name='correo']").val(),
                    nivel:$(c.v.i4 + " button.active").val()
                }}).done(function(data){
                var fun = function(d){
                    $(c.v.i4).modal("hide");
                    tools.pnotify.bottomright("Correcto", "El administrador se ha creado correctamente.", "success");
                    me.includeAdmin(d[0]);
                };
                tools.ajaxDone(data, fun);
            }).fail(tools.ajaxFail);
        });
    };
    this.showFNewAdmin = function(){
        var me = this;
        $("#Administradores .bt-new").click(function(){
            me.fNuevoAdmin();
        });
    };
    this.getAdministrador = function(id){
        var me = this;
        $.ajax({data:{o:3,m:4,id:id}}).done(function(data){
            var fun = function(d){
                 d = d[0];
                me.includeAdmin(d);
            };
            tools.ajaxDone(data, fun);
        }).fail(tools.ajaxFail);
    };
    this.cambiaNivel = function(correo){
        var me = this;
        var adm = _(me.administradores).find(function(x) { return x.Correo === correo;});
        c.confirm("¿Está seguro de que desea cambiar el nivel del administrador "+adm.Correo+"?");
        $("#confirm .true").unbind("click").bind("click", function(){
            $("#confirm").modal("hide");
            $.ajax({data:{o:3,m:3,correo:correo,a:1}}).done(function(data){
                var fun = function(d){
                    d = d[0];
                    me.includeAdmin(d);
                };
                tools.ajaxDone(data, fun);
            }).fail(tools.ajaxFail);
        });
    };
    this.cambiaEstado = function(correo){
        var me = this;
        var adm = _(me.administradores).find(function(x) { return x.Correo === correo;});
        c.confirm("¿Está seguro de que desea cambiar el estado del administrador "+adm.Correo+"?");
        $("#confirm .true").unbind("click").bind("click", function(){
            $("#confirm").modal("hide");
            $.ajax({data:{o:3,m:3,correo:correo,a:2}}).done(function(data){
                var fun = function(d){
                    d = d[0];
                    me.includeAdmin(d);
                };
                tools.ajaxDone(data, fun);
            }).fail(tools.ajaxFail);
        });
    };
    this.elimina = function(correo){
        var me = this;
        var adm = _(me.administradores).find(function(x) { return x.Correo === correo;});
        c.confirm("¿Está seguro de que desea eliminar al administrador "+adm.Correo+"?");
        $("#confirm .true").unbind("click").bind("click", function(){
            $("#confirm").modal("hide");
            $.ajax({data:{o:3,m:3,correo:correo,a:3}}).done(function(data){
                var fun = function(d){
                    d = d[0];
                    me.removeAdmin(d);
                };
                tools.ajaxDone(data, fun);
            }).fail(tools.ajaxFail);
        });
    };
};

var admin = new Administrador();