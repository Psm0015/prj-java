if(sessionStorage.getItem('loged') == 'true'){
    console.log(sessionStorage.getItem('login'))
    document.getElementById("Titulo").innerHTML="";
    document.getElementById("fsl").innerHTML="<p id='Titulo'>Olá,"+sessionStorage.getItem('nome')+"!</p>";
    console.log(sessionStorage.getItem('roles'))
    if(sessionStorage.getItem('roles') === "Admin"){
        console.log('ADMMODE ENABLED')
        document.getElementById('adm').innerHTML= '<a href="Admin.html">Administração</a>'
    }
}
ajax = new XMLHttpRequest();
function logar(){
    const data = JSON.stringify({
        // "username": "pedro.mota@df.estudante.senai.br",
        // "password": "33570870Pp"
        "username": document.getElementById("emaillogin").value,
        "password": document.getElementById("senhalogin").value
      });
      
    //   ajax.withCredentials = true;
      
      
      ajax.open("POST", "http://localhost:8080/noauth/login");
      ajax.setRequestHeader("cookie", "JSESSIONID=552EE52BE8EB819FFD5CE2A68463EC5A");
      ajax.setRequestHeader("Content-Type", "application/json");
      
      ajax.send(data);
      ajax.onload = function(){
        const dados = JSON.parse(this.responseText);
        if(this.status == 200){
            // console.log(dados);
            sessionStorage.setItem('login', dados.login);
            sessionStorage.setItem('nome', dados.nome);
            sessionStorage.setItem('token', (dados.token).replace("Bearer ",""));
            sessionStorage.setItem('roles', dados.roles[0]);
            let carrinho = [];
            cart = JSON.stringify(carrinho);
            sessionStorage.setItem('cart',cart)
            sessionStorage.setItem('loged','true')
            document.getElementById("fsl").innerHTML="Olá,"+dados.nome+"!";
            // console.log(sessionStorage.getItem('login'))
            // console.log(sessionStorage.getItem('nome'))
            // console.log(sessionStorage.getItem('token'))
            // console.log(sessionStorage.getItem('roles'))
            window.location.href = 'index.html';
        }
      }
    
}
function sair(){
    sessionStorage.setItem('login', '');
    sessionStorage.setItem('nome', '');
    sessionStorage.setItem('token', '');
    sessionStorage.setItem('roles', '');
    sessionStorage.setItem('cart','');
    sessionStorage.setItem('loged','false');
    window.location.href = "index.html";
}

