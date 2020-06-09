import model.Album;
import model.Photo;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    Session session;

    public static void main(String[] args) {
        Main main = new Main();
        //UWAGA. strategia bazy zostala ustawiona na CREATE w hibernate.cfg.xml

        // zaladuj przykladowe dane
       main.addNewData();
        main.addLiketoPhoto(1,1); //OK
        main.addLiketoAlbum(1,1);
        //4.1 usunięcie polubienia nie wpłynie na pozostałe elementy,
       // main.removeLike(1,1); //OK

        // 4.2 usunięcie zdjęcia usunie jego polubienia,ś
       // main.removePhotoAndLikes(1);  //OK
        //4.3 usunięcie albumu usunie zdjęcia w nim zawarte (wraz z konsekwencjami usunięcia zdjęcia),

       // main.removeAlbumAndPhoto(1); //OK

        // 4.4. usunięcie użytkownika usunie jego albumy (wraz z konsekwencjami usunięcia albumu) - OK.
        // main.deleteUserFromDB(1); //OK
        // 5.0 Dodoano mozliwosc polubienia nowego uzytkownika
        main.giveLikeToUser(1,1);

        main.close();
    }

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
        HibernateUtil.shutdown();
    }

    public void giveLikeToUser(long user1_Id, long user2_Id){
        Transaction transaction = session.beginTransaction();
        User userObject1 = (User) session.get(User.class, user1_Id);
        User userObject2 = (User) session.get(User.class, user1_Id);
        userObject1.addFriend(userObject2);

        session.save(userObject1);
        transaction.commit();
    }

    public void addLiketoPhoto(long userId, long photoId){
        Transaction transaction = session.beginTransaction();
        User userObject = (User) session.get(User.class, userId);
        Photo photoObject = (Photo) session.get(Photo.class, photoId);
        photoObject.addLike(userObject);
      // userObject.addLikeForPhoto(photoObject);
        session.save(photoObject);
        transaction.commit();
    }

    public void addLiketoAlbum(long userId, long albumId){
        Transaction transaction = session.beginTransaction();
        User userObject = (User) session.get(User.class, userId);
        Album albumObject = (Album) session.get(Album.class, albumId);
        //albumObject.a(userObject);
         userObject.addLikeForAlbum(albumObject);
        session.save(userObject);
        transaction.commit();
    }

    public void removeAlbumAndPhoto(long albumId){

        Transaction transaction = session.beginTransaction();
        //User userObject = (User) session.get(User.class, userId);
        Album albumObject = (Album) session.get(Album.class, albumId);
       // userObject.removeLikeForAlbum(albumObject); //usun z uzytkownika Album
        session.delete(albumObject); //usun caly album
        transaction.commit();
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


        album1.getPhotos().add(photo1);
        album1.addPhoto(photo2);

        album2.getPhotos().add(photo3);
        album2.addPhoto(photo4);


      //  user1.getAlbums().add(album1); //ok
       //user1.getAlbums().remove(album1); //ok
     //  photo1.addLike(user3);
        // user1.addLikeForPhoto(photo1);

//        user2.addLikeForAlbum(album2);
//        album2.addPhoto(photo3);
//        album2.addPhoto(photo4);
       // photo3.addLike(user2);
        // user2.addLikeForPhoto(photo3);

        //  user3.addLikeForAlbum(album1);
        //   user3.addLikeForAlbum(album2);
//        photo2.addLike(user3);
//        photo3.addLike(user3);
        // user3.addLikeForPhoto(photo3);
        //user 1 like user2
     //   user1.addFriend(user2);


        //zapis obiektow do bazy
        Transaction transaction = session.beginTransaction();
        session.save(user1); // najpierw rodzic
        session.save(user2); // najpierw rodzic
        session.save(album1); //dziecko po rodzicu
        session.save(album2); //dziecko po rodzicu
//        session.save(user2);
//        session.save(user3);
        transaction.commit();


    }
    //usuwa uzytkownika i wszystkie jego albumy
    public void deleteUserFromDB(long userToDelete) {

        User userObject = (User) session.get(User.class, userToDelete);
        session.delete(userObject);
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        }



    public void removeLike(long userId, long photoId) {

        User userObject = (User) session.get(User.class, userId);
        Photo photoObject = (Photo) session.get(Photo.class, photoId);
        System.out.println(userObject.getUsername());
        System.out.println(photoObject.getName());

        Transaction transaction = session.beginTransaction();
        //userObject.removeLikeFromPhoto(photoObject);
        photoObject.removeLike(userObject);
        transaction.commit();
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
