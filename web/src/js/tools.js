var Tools = function(){
    this.errText = {
        noCom : "No se ha logrado establecer la conexión con el servidor, revise su conectividad a internet y vuelva a intentarlo nuevamene más tarde.",
        erIne : "Ha ocurrido un error inesperado mientras se ejecutaba la operación, por favor vuelva a intentarlo más tarde."
    };
    this.pnotify ={
        bottomright : function(title, msg, type){
            var opts = {
                title: title,
                text: msg,
                type: type,
                nonblock: true
            };
            $.pnotify(opts);
        }
    };
    this.ajaxStart = function(){
        $("#loading").fadeIn(200);
    };
    this.ajaxFinish = function(){
        $("#loading").fadeOut(200);
    };
    this.fTemplate = _.template('<div class="modal-header">'+
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
                '<h3><%= title %></h3>'+
            '</div>'+
            '<form id="<%= id %>" style="margin:0">'+
                '<div class="modal-body"><%= form %></div>'+
                '<div class="modal-footer">'+
                    '<a href="#" role="button" data-dismiss="modal" class="btn">Cerrar</a>'+
                    '<button class="btn btn-primary">Guardar</button>'+
                '</div>'+
            '</form>');
    this.randomString = function(largo){
        var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
	var string_length = largo;
	var randomstring = '';
	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	return randomstring;
    };
    this.returnIndex = function(items, f){
        var x;
        for(var i in  items) {
          var item = items[i];
          if (f(item)) x = i;
        };
        return x;
    };
    this.msg = {
        errIne : function(){tools.pnotify.bottomright("Error", tools.errText.erIne, "error");},
        msg : function(msg){tools.pnotify.bottomright("Error", msg, "error");},
        noCon : function(){tools.pnotify.bottomright("Error", tools.errText.noCom, "error");},
        per : function(title, men, type){tools.pnotify.bottomright(title, men, type);}
    };
    this.ajaxDone = function(data, fun, funb){
        console.log(data);
        try{
            var d = JSON.parse(data);
            if(d.err === "0"){
                if(typeof fun === "function"){
                    fun(d.obj);
                }
            }
            else{
                if(typeof funb === "function"){
                    funb(d.obj);
                }else{
                    tools.msg.msg(d.msg);
                }
            }
        }
        catch(e){
            console.log(e);
            tools.msg.errIne();
        }
    };
    this.ajaxFail = function(){
        tools.msg.noCon();
    };
    this.printDate = function(d){
        var temp = d.split(" ");
        var parts = temp[0].split("-");
        switch(parseInt(parts[1])){
            case 1:parts[1]="Enero";break;
            case 2:parts[1]="Febrero";break;
            case 3:parts[1]="Marzo";break;
            case 4:parts[1]="Abril";break;
            case 5:parts[1]="Mayo";break;
            case 6:parts[1]="Junio";break;
            case 7:parts[1]="Julio";break;
            case 8:parts[1]="Agosto";break;
            case 9:parts[1]="Septiembre";break;
            case 10:parts[1]="Octubre";break;
            case 11:parts[1]="Noviembre";break;
            case 12:parts[1]="Diciembre";break;
        }
        return parts.reverse().join('-') + " " + temp[1].substr(0,8);
    };
    this.confirm = function(text){
        $("#confirm").modal("show").find("p").html(text);
        $("#confirm .true").focus();
    };
};

var tools = new Tools();