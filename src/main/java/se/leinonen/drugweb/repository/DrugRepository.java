package se.leinonen.drugweb.repository;

import se.leinonen.drugweb.model.Drug;

import java.util.List;

/**
 * Created by leinonen on 2014-07-02.
 */
public interface DrugRepository {
    void save(Drug drug);

    Drug findRandom();

    Drug findById(Long id);

    List<Drug> getAll();

    List<Drug> getListByName(String name);
}
