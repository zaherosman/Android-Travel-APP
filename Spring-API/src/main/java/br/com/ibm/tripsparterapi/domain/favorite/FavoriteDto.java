package br.com.ibm.tripsparterapi.domain.favorite;

public class FavoriteDto {
    private Integer favoriteId, userId, tripId;

    public FavoriteDto() {
    }

    public FavoriteDto(Integer favoriteId, Integer userId, Integer tripId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.tripId = tripId;
    }

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
}
