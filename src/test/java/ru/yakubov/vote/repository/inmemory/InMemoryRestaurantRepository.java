package ru.yakubov.vote.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryRestaurantRepository extends InMemoryBaseRepository<Restaurants>  implements RestaurantRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRestaurantRepository.class);

    public void init()
    {
        map.clear();
        put(RestaurantTestData.restaurant1);
        put(RestaurantTestData.restaurant2);
        counter.getAndSet(RestaurantTestData.RESTAURANT_ID2+1);
    }

    @Override
    public List<Restaurants> getAll() {
        return new ArrayList<>(map.values());
    }

}
