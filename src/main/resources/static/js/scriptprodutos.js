ajax = new XMLHttpRequest();
var prList;
token=sessionStorage.getItem('token');
listar()
function listar(){
    ajax.open("GET","http://localhost:8080/usuario/buscarprds");
    ajax.setRequestHeader("Authorization", "Bearer "+token);
    ajax.send();
    ajax.onload = function(){
        if(this.status == 200){
            prList=this.responseText;
            prList=JSON.parse(prList);
            //console.log(prList)
            txt = " "
            p = 0;
            for(const i of prList){
                //txt += "Id: "+i.id+"<br>"
                txt += "<tr><td>"+i.nome+"</td>"
                txt += "<td>"+i.descricao+" </td>"
                txt += "<td>"+i.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })+" </td>"
                txt += "<td><input type='button' value='Editar' onclick='editar("+p+")' class='btn btn-success' data-bs-toggle='modal' data-bs-target='#modaleditar'>   "
                txt += "<input type='button' value='Apagar' onclick='apagar("+p+")' class='btn btn-danger' data-bs-toggle='modal' data-bs-target='#modalapagar'></td></tr>"

                p++
            }
            document.getElementById("ListaProdutos").innerHTML = txt;
        }else if((this.status == 403)){
            window.location.href ="index.html";
        }
        
    }
}
function incluir(){
    var produto = {}
    produto.nome = document.getElementById("addnome").value;
    produto.descricao = document.getElementById("adddesc").value;
    produto.valor = document.getElementById("addvalor").value;
    produto.img = document.getElementById("addimg").value;
    ajax.open("POST","http://localhost:8080/adm/addprd");
    ajax.setRequestHeader("Authorization", sessionStorage.getItem('token'));
    ajax.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    ajax.send(JSON.stringify(produto));
    ajax.onload = function(){
        document.getElementById("addnome").value =''
        document.getElementById("adddesc").value=''
        document.getElementById("addvalor").value=''
        document.getElementById("addimg").value=''
        if(this.status == 200){
            alertasucesso('Produto inserido com <strong>Sucesso!</strong>');
        }
        listar()
    }
}
function editar(p){
    p= prList[p];
    document.getElementById("ided").value = p.id;
    document.getElementById("nomeed").value = p.nome;
    document.getElementById("desced").value = p.descricao;
    document.getElementById("valored").value = p.valor;
    document.getElementById("imged").value = p.img;
}
function editarconfirm(){
    pred = {}
    pred.id = document.getElementById("ided").value;
    pred.nome = document.getElementById("nomeed").value;
    pred.descricao = document.getElementById("desced").value;
    pred.valor = document.getElementById("valored").value;
    pred.img = document.getElementById("imged").value;
    ajax.open("PUT","http://localhost:8080/adm/atualizarprd");
    ajax.setRequestHeader("Authorization", sessionStorage.getItem('token'));
    ajax.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    ajax.send(JSON.stringify(pred));
    ajax.onload = function(){
        if(this.status == 200){
            alertasucesso('Produto editado com <strong>Sucesso!</strong>');
        }
        listar();
    }
}
function apagar(p){
    p= prList[p];
    document.getElementById("idapgr").value = p.id;
    document.getElementById("nomeapgr").innerHTML = "Nome: "+p.nome;
    document.getElementById("descapgr").innerHTML = "Descrição: "+p.descricao;
    document.getElementById("valorapgr").innerHTML = "Valor: "+p.valor;
}
function apagarconfirm(){
    idApgr=document.getElementById("idapgr").value
    ajax.open("DELETE","http://localhost:8080/adm/deletarprd/"+idApgr);
    ajax.setRequestHeader("Authorization", "Bearer "+token);
    ajax.send();
    ajax.onload = function(){
        if(this.status == 200){
            alertasucesso('Produto apagado com <strong>Sucesso!</strong>');
        }
        listar();
    }
}
function alertasucesso(msg){
    document.getElementById("alerta").innerHTML='<div class="alert alert-success">'+msg+'</div>'
    setTimeout(function(){document.getElementById("alerta").innerHTML=''},3000)
}
function sair(){
    sessionStorage.setItem('token', '');
}