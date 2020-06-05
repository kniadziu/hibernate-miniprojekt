import model.Album;
import model.Photo;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		// tu wstaw kod aplikacji

		main.addNewData();

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

	public void addNewData(){

		User user1=new User("Nowak", "2020-03-01");
		User user2=new User("Kowalski", "2020-03-11");
		User user3=new User("Wielgus", "2020-04-01");

		Photo photo1=new Photo("morze", "2020-02-01");
		Photo photo2=new Photo("las", "2020-02-01");
		Photo photo3=new Photo("park", "2020-02-01");
		Photo photo4=new Photo("inny", "2020-02-01");


		Album album1=new Album("Rodzina", "wycieczka 2020");
		Album album2=new Album("Wakacje", "Wakacje 2020");

		user1.addLikeForAlbum(album1);
		album1.addPhoto(photo1);
		photo1.addLike(user1);

		user2.addLikeForAlbum(album2);
		album2.addPhoto(photo2);
		album2.addPhoto(photo3);
		photo3.addLike(user2);


		user3.addLikeForAlbum(album1);
		user3.addLikeForAlbum(album2);
		photo2.addLike(user3);
		photo3.addLike(user3);

		//user 1 like user2
		user1.addFriend(user2);


		//zapis obiektow do bazy
		Transaction transaction = session.beginTransaction();
		session.save(user1); // gdzie user1 to instancja nowego Usera
		session.save(user2);
		session.save(user3);
		transaction.commit();
	}




}
