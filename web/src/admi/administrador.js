var Administrador = function(){
    this.administradores = new Array();
    this.table = "<% _.each(as, function(ad){ %> <tr><td><%= ad.Id %></td><td><%= ad.Correo %></td><td><%= ad.Nivel==0?'Super Administrador':'Administrador' %></td><td><%= ad.UltimaSesion == 'NULL' ? 'No ha iniciado sesión' : tools.printDate(ad.UltimaSesion) %></td><td><span class='label <%= ad.Estado==1?\"label-success'>Activo\":\"label-warning'>Inactivo\" %></span></td></tr> <% }) %>";
    this.printTable = function(){
        var me = this;
        $("#Administradores table tbody").html(_.template(me.table, {as : me.administradores}));
    };
    this.getAdministradores = function(){
        var me = this;
        $.ajax({data:{o:3,m:1}}).done(function(data){
            try{
                var dato = JSON.parse(data);
                var toGive = [{
                    "0" : dato[1],
                    "err" : dato[0][0].err
                }];
                var fun = function(d){
                    d = d[0];
                    me.administradores = new Array();
                    for(var x in d){
                        me.administradores.push(d[x]);
                    }
                    me.printTable();
                };
                tools.ajaxDone(JSON.stringify(toGive), fun);
            }catch(e){
                console.log(e);
                tools.msg.errIne();
            }
        }).fail(tools.ajaxFail);
    };
};

var admin = new Administrador();