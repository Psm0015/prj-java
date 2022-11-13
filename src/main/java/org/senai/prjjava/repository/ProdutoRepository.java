package org.senai.prjjava.repository;
import org.springframework.data.repository.CrudRepository;
import org.senai.prjjava.entity.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{

}
