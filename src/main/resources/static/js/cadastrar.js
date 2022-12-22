const xhr = new XMLHttpRequest();
xhr.withCredentials = true;
function cadastrar(){
    preenche();

    document.getElementById('email').disabled=true;
    document.getElementById('primeironome').disabled=true;
    document.getElementById('sobrenome').disabled=true;
    document.getElementById('cep').disabled=true;
    document.getElementById('endereco').disabled=true;
    document.getElementById('numero').disabled=true;
    document.getElementById('cidade').disabled=true;
    document.getElementById('estado').disabled=true;
    document.getElementById('senha').disabled=true;
    document.getElementById('confirmarsenha').disabled=true;

    document.getElementById('ebtn').disabled=true
    document.getElementById('ebtn').innerHTML+='<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true">';
    // document.getElementById('fbtn').disabled=true
    // document.getElementById('fbtn').innerHTML+='<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true">';



    stts = true;
    login = document.getElementById('email').value;
    primeiro_nome=document.getElementById('primeironome').value;
    sobrenome=document.getElementById('sobrenome').value;
    cep=document.getElementById('cep').value;
    endereco=document.getElementById('endereco').value;
    numero=document.getElementById('numero').value;
    cidade=document.getElementById('cidade').value;
    estado=document.getElementById('estado').value;
    password=document.getElementById('senha').value;
    confirmarsenha= document.getElementById('confirmarsenha').value    
    
    if(!((/\S+@\S+\.\S+/).test(login))){
        document.getElementById('emails').innerHTML="Email Inválido!"
        document.getElementById('emails').style = "color: red;"
        document.getElementById('email').className="form-control is-invalid"
        stts = false;
    }
    if(password.length<8){
        document.getElementById('senhas').innerHTML="A senha precisa ter no mínimo 8 caracteres"
        document.getElementById('senhas').style = "color: red;"
        document.getElementById('senha').className="form-control is-invalid"
        stts = false;
    }
    if(password != confirmarsenha){
        document.getElementById('senha').className="form-control is-invalid"
        document.getElementById('confirmarsenha').className="form-control is-invalid"
        document.getElementById('confirmarsenhas').style = "color: red;"
        document.getElementById('confirmarsenhas').innerHTML="As senhas precisam ser iguais!"
        stts = false;
    }
    if(primeiro_nome.length<3){
        document.getElementById('primeironome').className="form-control is-invalid"
        document.getElementById('primeironomes').style = "color: red;"
        document.getElementById('primeironomes').innerHTML="Insira um nome válido!"
        stts = false;
    }
    if(sobrenome.length<3){
        document.getElementById('sobrenome').className="form-control is-invalid"
        document.getElementById('sobrenomes').style = "color: red;"
        document.getElementById('sobrenomes').innerHTML="Insira um sobrenome válido!"
        stts = false;
    }
    if(cep.length<8){
        document.getElementById('cep').className="form-control is-invalid"
        document.getElementById('ceps').style = "color: red;"
        document.getElementById('ceps').innerHTML="Insira um CEP válido!"
        stts = false;
    }
    if(endereco.length<1){
        document.getElementById('endereco').className="form-control is-invalid"
        document.getElementById('enderecos').style = "color: red;"
        document.getElementById('enderecos').innerHTML="Insira um endereço válido!"
        stts = false;
    }
    if(numero.length<1){
        document.getElementById('numero').className="form-control is-invalid"
        document.getElementById('numeros').style = "color: red;"
        document.getElementById('numeros').innerHTML="Insira um numero válido!"
        stts = false;
    }
    if(cidade.length<1){
        document.getElementById('cidade').className="form-control is-invalid"
        document.getElementById('cidades').style = "color: red;"
        document.getElementById('cidades').innerHTML="Insira uma cidade válida!"
        stts = false;
    }
    if(estado.length<1){
        document.getElementById('estado').className="form-control is-invalid"
        document.getElementById('estados').style = "color: red;"
        document.getElementById('estados').innerHTML="Insira um estado válido!"
        stts = false;
    }

    if(stts){
        const novousuario = JSON.stringify({
            "login":login,
            "primeiro_nome":primeiro_nome,
            "sobrenome":sobrenome,
            "cep":cep,
            "endereco":endereco,
            "numero":numero,
            "cidade":cidade,
            "estado":estado,
            "password":password
        });
        xhr.open("POST", "http://localhost:8080/noauth/novo");
          xhr.setRequestHeader("cookie", "JSESSIONID=552EE52BE8EB819FFD5CE2A68463EC5A");
          xhr.setRequestHeader("Content-Type", "application/json");
          
        xhr.send(novousuario);
    
        console.log(novousuario);
        xhr.onload = function(){
            console.log(this.status)
            if(this.status == 200){
                window.location.href = "confirmarEmail.html";
            }else if(this.status == 406){
                document.getElementById("sttspag").style = "color: red;"
                document.getElementById("sttspag").innerHTML="Esse usuário já existe!"
            }else{
                document.getElementById("sttspag").style = "color: red;"
                document.getElementById("sttspag").innerHTML="Erro desconhecido, entre contato com o suporte!"
            }
        }
    }
    
}

function preenche(){

    document.getElementById('primeironome').value = "Pedro";
    document.getElementById('sobrenome').value  = "Santos da Mota";
    document.getElementById('cep').value  = "72318027";
    document.getElementById('endereco').value  = "Qr 402 conjunto 26";
    document.getElementById('numero').value  = "33";
    document.getElementById('cidade').value  = "Brasília";
    document.getElementById('estado').value  = "Distrito Federal";
    document.getElementById('senha').value  = "33570870Pp";
    document.getElementById('confirmarsenha').value  = "33570870Pp";
}