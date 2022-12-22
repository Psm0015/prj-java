vitrine = document.getElementById('produtosvtn')
const ajax = new XMLHttpRequest();
ajax.withCredentials = true;
ajax.open("GET", "http://localhost:8080/noauth/buscarprds");
ajax.send();
ajax.onload = function(){
    produtos = JSON.parse(this.responseText);
    console.log(produtos.length)
    cardsg = ''
    i=0
    for(let c = 0;c < produtos.length;c+=3){
        console.log(c)
        cards = ''
        for(i; i < c+3;i++){
            if(i< produtos.length){
                // console.log('id '+c + 'prd' + (i+1))
                cards+= '<div class="card"><img class="card-img-top" src="'+produtos[i].img+'" alt="Card image cap"><div class="card-body"><h5 class="card-title">'+produtos[i].nome+'</h5><p class="card-text">'+produtos[i].descricao+'</p><p class="card-text"><h6 class="text" style="color: rgb(78, 78, 255);">'+produtos[i].valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })+'</h6></p><button id="ebtn'+produtos[i].id+'" type="button" onclick="addcart('+produtos[i].id+')" class="btn btn-primary">Adicionar ao Carrinho</button></div></div>'
            }
        }
        cardsg += '<div class="card-group">'+cards+'</div>'
    }
    vitrine.innerHTML = cardsg;

}
function addcart(prdid){

    if(sessionStorage.getItem('loged') == 'true'){
        obj = JSON.parse(sessionStorage.getItem('cart'))
        obj.push(prdid)
        sessionStorage.setItem('cart',JSON.stringify(obj))
        console.log(obj)
        obj.pop();
        document.getElementById('ebtn'+prdid).className = 'btn btn-success'
        document.getElementById('ebtn'+prdid).innerHTML = 'Adicionado'
        document.getElementById('ebtn'+prdid).disabled = true
        setTimeout(() => {
            document.getElementById('ebtn'+prdid).className = 'btn btn-primary'
            document.getElementById('ebtn'+prdid).innerHTML = 'Adicionar ao Carrinho'
            document.getElementById('ebtn'+prdid).disabled = false
        }, 1000);
    }else{
        document.getElementById('ebtn'+prdid).className = 'btn btn-danger'
        document.getElementById('ebtn'+prdid).innerHTML = 'Faça login'
        document.getElementById('ebtn'+prdid).disabled = true
        setTimeout(() => {
            document.getElementById('ebtn'+prdid).className = 'btn btn-primary'
            document.getElementById('ebtn'+prdid).innerHTML = 'Adicionar ao Carrinho'
            document.getElementById('ebtn'+prdid).disabled = false
        }, 3000);
    }
}
