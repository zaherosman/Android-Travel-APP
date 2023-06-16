package br.com.ibm.tripsparterapi.repositories;

import br.com.ibm.tripsparterapi.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository <Favorite, Integer> {

    Optional<Favorite>findByUserIdAndTripId(Integer userId, Integer tripId);

    Optional<Favorite> findByFavoriteId(Integer favoriteId);

    Optional<List<Favorite>> findAllByUserId(Integer userId);

    Optional<List<Favorite>> findAllByUserIdAndTripId(Integer userId, Integer tripId);

}
