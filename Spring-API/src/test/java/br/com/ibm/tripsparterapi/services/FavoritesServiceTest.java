package br.com.ibm.tripsparterapi.services;

import br.com.ibm.tripsparterapi.domain.favorite.FavoriteDto;
import br.com.ibm.tripsparterapi.entities.Favorite;
import br.com.ibm.tripsparterapi.repositories.FavoriteRepository;
import br.com.ibm.tripsparterapi.services.exceptions.FavoriteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

public class FavoritesServiceTest {
    private Integer favoriteId = 1;
    private Integer userId = 1;
    private Integer tripId = 1;

    @InjectMocks
    private FavoritesService favoritesService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsertFavorite(){
        FavoriteDto favoriteDto = new FavoriteDto(favoriteId, userId, tripId);
        Mockito.when(favoriteRepository.findAllByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId))).thenReturn(Optional.empty());

        Favorite favorite = new Favorite(favoriteId, userId, tripId);
        Mockito.when(favoriteRepository.save(ArgumentMatchers.any())).thenReturn(favorite);

        FavoriteDto favoriteDto1 = favoritesService.insertFavorite(favoriteDto);
        Mockito.verify(favoriteRepository).findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId));
        Mockito.verify(favoriteRepository).save(ArgumentMatchers.any());
        Assertions.assertEquals(favoriteId, favoriteDto1.getFavoriteId());
        Assertions.assertEquals(userId, favoriteDto1.getUserId());
        Assertions.assertEquals(tripId, favoriteDto1.getTripId());
    }

    @Test
    public void testInsertFavoriteException(){
        Integer id = 2;
        FavoriteDto favoriteDto = new FavoriteDto(favoriteId, userId, tripId);
        Favorite favorite = new Favorite(favoriteId, userId, tripId);

        Mockito.when(favoriteRepository.findByUserIdAndTripId(ArgumentMatchers.eq(userId),ArgumentMatchers.eq(tripId))).thenReturn(Optional.of(favorite));
        FavoriteException favoriteException = Assertions.assertThrows(FavoriteException.class, () -> {
            favoritesService.insertFavorite(favoriteDto);
        }, "ExistingFavoriteException error was expected");
        Mockito.verify(favoriteRepository).findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId));
        Assertions.assertNotNull(favoriteException);
    }

    @Test
    public void testGetAllFavorites(){
        List<Favorite> favorites = Arrays.asList(new Favorite(favoriteId, userId, tripId));
        Mockito.when(favoriteRepository.findAll()).thenReturn(favorites);

        List<FavoriteDto> expected = favoritesService.getAllFavorites();

        Mockito.verify(favoriteRepository).findAll();

        Assertions.assertEquals(expected.size(), 1);
        Assertions.assertEquals(expected.get(0).getFavoriteId(), favorites.get(0).getFavoriteId());
        Assertions.assertEquals(expected.get(0).getFavoriteId(), favorites.get(0).getUserId());
        Assertions.assertEquals(expected.get(0).getTripId(), favorites.get(0).getTripId());
    }

    @Test
    public void TestGetAllFavoritesEmpty(){
        List<Favorite> favorites = new ArrayList<>();
        Mockito.when(favoriteRepository.findAll()).thenReturn(favorites);

        List<FavoriteDto> expected = favoritesService.getAllFavorites();

        Mockito.verify(favoriteRepository).findAll();

        Assertions.assertEquals(expected.size(), 0);
    }

    @Test
    public void TestGetFavoriteByFavoriteId(){
        Integer id = 2;
        Optional<Favorite> favorite = Optional.of(new Favorite(favoriteId, userId, tripId));
        Mockito.when(favoriteRepository.findByFavoriteId(ArgumentMatchers.eq(favoriteId))).thenReturn(favorite);

        Favorite expected = favoritesService.getFavoriteByFavoriteId(favoriteId);

        Mockito.verify(favoriteRepository).findByFavoriteId(ArgumentMatchers.eq(favoriteId));

        Assertions.assertEquals(expected.getFavoriteId(), favorite.get().getFavoriteId());
    }

    @Test
    public void TestGetFavoriteByFavoriteIdException(){
        Optional<Favorite> favorite = Optional.empty();
        Mockito.when(favoriteRepository.findByFavoriteId(ArgumentMatchers.eq(favoriteId))).thenReturn(favorite);

        FavoriteException favoriteException = Assertions.assertThrows(FavoriteException.class, () -> {
            favoritesService.getFavoriteByFavoriteId(favoriteId);
        }, "ExistingFavoriteException error was expected");

        Mockito.verify(favoriteRepository).findByFavoriteId(ArgumentMatchers.eq(favoriteId));

        Assertions.assertNotNull(favoriteException);
    }

    @Test
    public void TestGetFavoriteByUserId(){
        Optional<List<Favorite>> favorite = Optional.of(Arrays.asList(new Favorite(favoriteId, userId, tripId)));
        Mockito.when(favoriteRepository.findAllByUserId(ArgumentMatchers.eq(userId))).thenReturn(favorite);

        List<Favorite> expected = favoritesService.getFavoriteByUserId(userId);

        Mockito.verify(favoriteRepository).findAllByUserId(ArgumentMatchers.eq(userId));
        Assertions.assertEquals(expected.get(0).getUserId(), favorite.get().get(0).getUserId());
    }

    @Test
    public void TestGetFavoriteByUserIdException(){
        Optional<List<Favorite>> favorites = Optional.empty();
        Mockito.when(favoriteRepository.findAllByUserId(ArgumentMatchers.eq(userId))).thenReturn(favorites);

        FavoriteException favoriteException= Assertions.assertThrows(FavoriteException.class, () -> {
            favoritesService.getFavoriteByUserId(userId);
        }, "ExistingFavoriteException error was expected");

        Mockito.verify(favoriteRepository).findAllByUserId(ArgumentMatchers.eq(userId));

        Assertions.assertNotNull(favoriteException);
    }


    @Test
    public void TestGetFavoriteByUserIdAndTripID(){
        Optional<Favorite> favorite = Optional.of(new Favorite(favoriteId, userId, tripId));
        Mockito.when(favoriteRepository.findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId))).thenReturn(favorite);

        Favorite expected = favoritesService.getFavoriteByUserIdAndTripID(userId, tripId);

        Mockito.verify(favoriteRepository).findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId));

        Assertions.assertEquals(expected.getUserId(), favorite.get().getUserId());
        Assertions.assertEquals(expected.getTripId(), favorite.get().getFavoriteId());

    }

    @Test
    public void TestGetFavoriteByUserIdAndTripIDException(){
        Optional<Favorite> favorite = Optional.empty();
        Mockito.when(favoriteRepository.findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId))).thenReturn(favorite);

        FavoriteException favoriteException = Assertions.assertThrows(FavoriteException.class, () -> {
            favoritesService.getFavoriteByUserIdAndTripID(userId, tripId);
        }, "ExistindFavoriteException error was expected");

        Mockito.verify(favoriteRepository).findByUserIdAndTripId(ArgumentMatchers.eq(userId), ArgumentMatchers.eq(tripId));

        Assertions.assertNotNull(favoriteException);
    }

    @Test
    public void DeleteFavorite(){
        Favorite favorite = new Favorite(favoriteId, userId, tripId);
        Mockito.when(favoriteRepository.findAll()).thenReturn(Collections.singletonList(favorite));

        favoritesService.deleteFavorite(favoriteId);

        Mockito.verify(favoriteRepository).deleteById(ArgumentMatchers.eq(favoriteId));

        Assertions.assertEquals(favoriteId, 1);
    }


}
