ajax = new XMLHttpRequest();
var userList;
token=sessionStorage.getItem('token');
console.log(token);
function listar(){
    ajax.open("GET","http://localhost:8080/api/usuario/");
    ajax.setRequestHeader("Authorization", "Bearer "+token);
    ajax.send();
    ajax.onload = function(){
        if(this.status == 200){
            userList=this.responseText;
            userList=JSON.parse(userList);
            txt = " "
            u = 0;
            for(const i of userList){
                //txt += "Id: "+i.id+"<br>"
                txt += "<tr><td>"+i.login+"</td>"
                txt += "<td>"+i.nome+"</td>"
                txt += "<td>"+i.email+" </td>"
                txt += "<td>"+i.enabled+" </td>"
                txt += "<td><input type='button' value='Editar' onclick='editar("+u+")' class='btn btn-success' data-bs-toggle='modal' data-bs-target='#modaleditar'>   "
                txt += "<input type='button' value='Apagar' onclick='apagar("+u+")' class='btn btn-danger' data-bs-toggle='modal' data-bs-target='#modalapagar'></td></tr>"
        
        
                u++
            }
            document.getElementById("ListaUsers").innerHTML = txt
        }else if((this.status == 403)){
            window.location.href ="index.html";
        }
    }
}
listar();
function cadastrar(){
    var usuario = {};
    usuario.login = document.getElementById("addlogin").value
    usuario.nome = document.getElementById("addnome").value;
    usuario.email = document.getElementById("addemail").value;
    usuario.password = document.getElementById("addsenha").value;
    ajax.open("POST","http://localhost:8080/api/usuario/");
    ajax.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    ajax.setRequestHeader("Authorization", "Bearer "+token);
    ajax.send(JSON.stringify(usuario));
    ajax.onload = function(){
        if(this.status == 200){
            alertasucesso('Usuário inserido com <strong>Sucesso!</strong>');
        }
        document.getElementById("addnome").value='';
        document.getElementById("addemail").value='';
        listar();
    }
}
function editar(u){
    u = userList[u];
    document.getElementById("ided").value = u.id;
    document.getElementById("logined").value = u.login;
    document.getElementById("nomeed").value = u.nome;
    document.getElementById("emailed").value = u.email;
}
function editarconfirm(){
    useradd = {}
    useradd.id = document.getElementById("ided").value;
    useradd.login = document.getElementById("logined").value;
    useradd.nome = document.getElementById("nomeed").value;
    useradd.email = document.getElementById("emailed").value;
    useradd.password = document.getElementById("edsenha").value;
    ajax.open("PUT","http://localhost:8080/api/usuario/editar");
    ajax.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    ajax.setRequestHeader("Authorization", "Bearer "+token);
    ajax.send(JSON.stringify(useradd));
    ajax.onload = function(){
        if(this.status == 200){
            alertasucesso('Usuário editado com <strong>Sucesso!</strong>');
        }
        listar();
    }
}
function apagar(u){
    u = userList[u]
    document.getElementById("nomeapgr").innerHTML = "Nome: "+u.nome;
    document.getElementById("emailapgr").innerHTML = "Email: "+u.email;
    idpg=document.getElementById("idapgr").value = u.id;
}
function apagarconfirm(){
    ajax.open("DELETE","http://localhost:8080/api/usuario/"+idpg);
    ajax.setRequestHeader("Authorization", "Bearer "+token);
    ajax.send();
    ajax.onload = function(){
        if(this.status == 200){
            alertasucesso('Usuário apagado com <strong>Sucesso!</strong>');
        }
        listar();
    }
}
function alertasucesso(msg){
    document.getElementById("alerta").innerHTML='<div class="alert alert-success">'+msg+'</div>'
    setTimeout(function(){document.getElementById("alerta").innerHTML=''},3000)
}
function ver(senhaid){
    view=document.getElementById("ver"+senhaid)
    senhacamp=document.getElementById(senhaid)
    if (view.innerHTML === '<i class="fa-solid fa-eye"></i>'){
        view.innerHTML='<i class="fa-solid fa-eye-slash"></i>'
        senhacamp.type = 'text'
    } else{
        view.innerHTML='<i class="fa-solid fa-eye"></i>'
        senhacamp.type = 'password'
    }
    
}
function sair(){
    sessionStorage.setItem('token', '');
}