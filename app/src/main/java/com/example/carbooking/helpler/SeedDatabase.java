package com.example.carbooking.helpler;

import android.content.Context;

import com.example.carbooking.Entity.Category;
import com.example.carbooking.Entity.Order;
import com.example.carbooking.Entity.Role;
import com.example.carbooking.Entity.Status;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.Entity.User;
import com.example.carbooking.Entity.Vehicle;
import com.example.carbooking.repository.CategoryRepository;
import com.example.carbooking.repository.OrderRepository;
import com.example.carbooking.repository.RoleRepository;
import com.example.carbooking.repository.StatusRepository;
import com.example.carbooking.repository.TourRepository;
import com.example.carbooking.repository.UserRepository;
import com.example.carbooking.repository.VehicleRepository;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SeedDatabase {
    private final Context context;

    public SeedDatabase(Context context) {
        this.context = context;
    }

    public void Initialize() {
        UserRepository userRepository = new UserRepository(context);
        VehicleRepository vehicleRepository = new VehicleRepository(context);
        TourRepository tourRepository = new TourRepository(context);
        StatusRepository statusRepository = new StatusRepository(context);
        RoleRepository roleRepository = new RoleRepository(context);
        OrderRepository orderRepository = new OrderRepository(context);
        CategoryRepository categoryRepository = new CategoryRepository(context);
        if (roleRepository.getAllRole().size() == 0) {
            Role user = new Role();
            user.setId(1);
            user.setRoleName(1);
            Role manager = new Role();
            manager.setId(2);
            manager.setRoleName(2);
            roleRepository.createRole(user);
            roleRepository.createRole(manager);
        }
        if (userRepository.getAllUser().size() == 0) {
            User user = new User();
            user.setUserName("admin");
            user.setPassword("admin");
            user.setRole_id(2);
            user.setLocked(false);
            userRepository.createUser(user);
            User user1 = new User();
            user.setUserName("user1");
            user.setPassword("User12345");
            user.setRole_id(2);
            user.setLocked(false);
            userRepository.createUser(user1);
        }

        if(statusRepository.getAllStatus().size() == 0){
            Status status = new Status();
            status.setId(1);
            status.setStatusName("Completed");
            status.setStatusDesc("Completed");
            statusRepository.createStatus(status);
        }
        if (categoryRepository.getAllCategory().isEmpty()) {
            Category domestic = new Category("Domestic");
            Category international = new Category("International");
            categoryRepository.createCategory(domestic);
            categoryRepository.createCategory(international);
        }

        if (vehicleRepository.getAllVehicle().isEmpty()) {
            Vehicle bus = new Vehicle("Bus");
            Vehicle car = new Vehicle("Car");
            Vehicle motorbike = new Vehicle("Motorbike");
            Vehicle boat = new Vehicle("Boat");
            Vehicle helicopter = new Vehicle("Helicopter");
            vehicleRepository.createVehicle(bus);
            vehicleRepository.createVehicle(car);
            vehicleRepository.createVehicle(motorbike);
            vehicleRepository.createVehicle(boat);
            vehicleRepository.createVehicle(helicopter);
        }

        if (tourRepository.getAllTour().isEmpty()) {
            int categoriesSize = categoryRepository.getAllCategory().size();
            int vehicleSize = vehicleRepository.getAllVehicle().size();
            String[] titles = {"Small-Group Tour of the Cu Chi Tunnels", "Cu Chi Tunnels and Mekong Delta Tour", "Ninh Binh Full Day Trip", "Marble Mountains and Hoi An Tour", "Full-day Tour of Ba Na Hills with Buffet Lunch", "Sun World Ba Na Hills Ticket"};
            String[] images = {"https://q-xx.bstatic.com/xdata/images/xphoto/max1200/130013605.jpg?k=b6cf8e1c2a45670119c42904fb0e6fbe86ea2ca4665b2ee4b70778acc04c120b&o=", "https://r-xx.bstatic.com/xdata/images/xphoto/max1200/298835198.jpg?k=783ebd5d0bbbe95092c5e534fb0c6ca3d1816e269eb0192dc075b42728f1f098&o=", "https://r-xx.bstatic.com/xdata/images/xphoto/max1200/172497069.jpg?k=183fab37faf72e58e069125a2a8dc4f632af1a0fbf7697cbc66f6330e5fb1520&o=", "https://r-xx.bstatic.com/xdata/images/xphoto/max1200/130075674.jpg?k=739397298f9d93d564e4185ebfbdc542df1ab91a1a6efb6f107ef46bf897697d&o=", "https://r-xx.bstatic.com/xdata/images/xphoto/max1200/175185950.jpg?k=7056a02d0e5534e544740f512c13ef7c4817bf197fd0115b84bead6dd21cbc19&o=", "https://r-xx.bstatic.com/xdata/images/xphoto/max1200/175185882.jpg?k=3e2ee1e78b66394d1a51f98bf52d5b2c096bc5e01cf9a43606d38adfd2a70de5&o="};
            String[] locations = {"Ho Chi Minh City", "Ho Chi Minh City", "Ninh Binh", "Da Nang", "Da Nang", "Da Nang"};
            double[] prices = {990000, 1500000, 3000000, 7500000, 9990000, 12000000};
            for (int i = 0; i < 20; i++) {
                Tour tour = new Tour();
                tour.setTile(titles[i % titles.length]);
                tour.setLocationFrom("Ha Noi");
                tour.setLocationTo(locations[i % locations.length]);
                tour.setTourTime("5 ngày 4 đêm");
                tour.setDateNumber(5);
                tour.setTourSchdule("2024-07-15 — 2024-07-22");
                tour.setPricePerPerson(prices[i % prices.length]);
                tour.setContactNumber("0123456789");
                tour.setCategoryId((i % categoriesSize) + 1);
                tour.setVehicle((i % vehicleSize) + 1);
                tour.setDescription("On this sightseeing day tour, you'll discover the cultural and historical highlights of Ba Na Hills. After a hotel pickup or meeting your guide, you'll travel to the Ba Na cable car station. In your modern cable car, you'll first visit the Golden Bridge, famous for its two massive hand sculptures. You'll then continue your scenic cable car ride to explore the nine gardens of Le Jardin D’Amour and the Ling Ung Pagoda. From here, you'll visit a recreated French village, a monastery, Tombstone Temple and a tea shop.\n\n" +
                        "You'll then sit to eat a buffet lunch at a local restaurant. After lunch, you'll have free time to explore Fantasy Park, where you can stroll through the fairy forest, play various games and ride the amusement park's rides. You'll then take the cable car back to the station.");
                tour.setImage(images[i % images.length]);
                tour.setAvaliable(new Random().nextBoolean());
                tourRepository.createTour(tour);
            }
        }

        if(orderRepository.getAllOrder().isEmpty()){
        }
    }
}
