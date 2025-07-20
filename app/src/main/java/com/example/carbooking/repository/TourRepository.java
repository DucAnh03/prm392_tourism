package com.example.carbooking.repository;

import android.content.Context;

import com.example.carbooking.Entity.Tour;
import com.example.carbooking.dao.PRM392RoomDatabase;
import com.example.carbooking.dao.TourDao;

import java.util.List;

public class TourRepository {
    private TourDao tourDao;
    private PRM392RoomDatabase db;

    public TourRepository(Context context) {
        db = PRM392RoomDatabase.getInstance(context);
        tourDao = db.tourDao();
    }

//    public TourRepository(Context context) {
//        tourDao = PRM392RoomDatabase.getInstance(context).tourDao();
//        PRM392RoomDatabase db = PRM392RoomDatabase.getInstance(context);
//        List<Tour> tours = db.tourDao().selectAll();
//    }

    public void createTour(Tour tour) {
        db.runInTransaction(() -> {
            tourDao.insert(tour);
        });
    }

    public void updateTour(Tour tour) {
        db.runInTransaction(() -> {
            tourDao.update(tour);
        });
    }

    public Tour getTour(int tourId) {
        return tourDao.select(tourId);
    }

    public List<Tour> getAllTour() {
        return tourDao.selectAll();
    }

    public boolean updateTourVote(int tourId, int voteValue) {
        db.runInTransaction(() -> {
            Tour tour = tourDao.select(tourId);
            if (tour != null) {
                tour.setVotedNumber(tour.getVotedNumber() + 1);
                tour.setVoteScore(tour.getVoteScore() + voteValue);
                tourDao.update(tour);
            }
        });
        return true;
    }
}
