import model.Album;
import model.Photo;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Set;

public class Main {

    Session session;

    public static void main(String[] args) {
        Main main = new Main();
        // tu wstaw kod aplikacji

        main.addNewData();
        main.removePhotoAndLikes(1);
        //  main.removeLike(1,1);
        // main.deleteUserFromDB("Nowak");
        ///koniec kodu


        main.close();
    }

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
        HibernateUtil.shutdown();
    }

    public void addNewData() {

        User user1 = new User("Nowak", "2020-03-01");
        User user2 = new User("Kowalski", "2020-03-11");
        User user3 = new User("Wielgus", "2020-04-01");

        Photo photo1 = new Photo("morze", "2020-02-01");
        Photo photo2 = new Photo("las", "2020-02-01");
        Photo photo3 = new Photo("park", "2020-02-01");
        Photo photo4 = new Photo("inny", "2020-02-01");


        Album album1 = new Album("Rodzina", "wycieczka 2020");
        Album album2 = new Album("Wakacje", "Wakacje 2020");


        album1.addPhoto(photo1);
        //   user1.addLikeForAlbum(album1);

        photo1.addLike(user1);
        // user1.addLikeForPhoto(photo1);

        user2.addLikeForAlbum(album2);
        album2.addPhoto(photo2);
        album2.addPhoto(photo3);
        photo3.addLike(user2);
        // user2.addLikeForPhoto(photo3);

        //  user3.addLikeForAlbum(album1);
        //   user3.addLikeForAlbum(album2);
        photo2.addLike(user3);
        photo3.addLike(user3);
        // user3.addLikeForPhoto(photo3);
        //user 1 like user2
        user1.addFriend(user2);


        //zapis obiektow do bazy
        Transaction transaction = session.beginTransaction();
        session.save(user1); // gdzie user1 to instancja nowego Usera
        session.save(user2);
        session.save(user3);
        transaction.commit();


    }

    //usuwa uzytkownika i wszystkie jego albumy
    public void deleteUserFromDB(String userToDelete) {
        Query query = session.createQuery("FROM User u WHERE u.username = :nameToDelete");
        query.setString("nameToDelete", userToDelete);

        List<User> results = query.list();
//		for(User u : results){
//			System.out.println(u.getUsername());
//		}

        Transaction transaction = session.beginTransaction();
        if (results.size() > 0) {
            for (User user : results) {
                int i = user.getPhotosLiked().size();
                if (i > 0) {
                    for (Photo photo : user.getPhotosLiked()) {
                        user.removeLikeFromPhoto(photo);
                        // session.delete(photo);
                        //session.save(photo);
                    }
                }

                session.delete(user);
                session.save(user);
            }
            transaction.commit();
        }

    }

    public void removeLike(Integer userId, Integer photoId) {

        Query query = session.createQuery("FROM User u WHERE u.id= :userId");
        query.setInteger("userId", userId);

        List<User> results = query.list();


        Transaction transaction = session.beginTransaction();
        for (User u : results) {
            System.out.println(u.getUsername());
            for (Photo p : u.getPhotosLiked()) {
                if (p.getId() == photoId) {
                    // System.out.println(p.getName());
                    u.removeLikeFromPhoto(p);
                    p.removeLike(u);
                    session.persist(u);
                    session.persist(p);
//                    session.save(p);
//                    session.save(u);
                }
            }
        }

    }


//    public void removePhotoAndLikes(Integer photoId) {
//
//        Query query = session.createQuery("FROM Photo ph WHERE ph.id= :photoId");
//        query.setLong("photoId", photoId);
//
//        List<Photo> results = query.list();
//
//        Transaction transaction = session.beginTransaction();
//        for (Photo p : results) {
//
//            for (User u : p.getUsersLikes()) {
//                System.out.println(p.getName());
//                System.out.println(u.getUsername());
//                //  u.removeLikeFromPhoto(p);
//                p.removeLike(u);
//            }
//
//            session.delete(p);
//        }
//        transaction.commit();
//

        // delete transaction
//        int counter = 0;

//        for (Object result : results) {
//            session.delete(result);
//           // counter++;
//        }


//        System.out.println("Usunieto rekordow:" + counter);


  //  }


    public void removePhotoAndLikes(long photoId) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Album");

        Photo photoObject = (Photo) session.get(Photo.class, photoId);
        //usun Photo z Albumu najpierw
        List<Album> results = query.list();
        for (Album album : results) {
            album.removePhoto(photoObject);
        }
        session.delete(photoObject);
        transaction.commit();
    }


}
