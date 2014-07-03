package se.leinonen.drugweb.repository;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import se.leinonen.drugweb.model.Drug;

import java.util.List;

/**
 * Created by leinonen on 2014-06-28.
 */
public class DrugRepositoryImpl implements DrugRepository {

    private static DrugRepository instance;


    private DrugRepositoryImpl() {
    }

    public static DrugRepository getInstance() {
        if (instance == null){
            instance = new DrugRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(Drug drug){
        Ebean.save(drug);
    }

    @Override
    public Drug findRandom(){
        int max = Ebean.find(Drug.class).findRowCount();
        int rand = (int) Math.floor(Math.random() * max);
        return Ebean.find(Drug.class, rand);
    }

    @Override
    public Drug findById(Long id){
        return Ebean.find(Drug.class, id);
    }


    @Override
    public List<Drug> getAll(){
        return Ebean.find(Drug.class).findList();
    }

    @Override
    public List<Drug> getListByName(String name){
        return Ebean.find(Drug.class).where(Expr.like("name", "%" + name + "%")).findList();
    }
}
