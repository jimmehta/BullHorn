package customTools;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import util.MD5Util;
import model.Bhuser;

public class DBUser {
	public static Bhuser getUser(int userID)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		Bhuser user = em.find(Bhuser.class, userID);
		return user;		
	}
	public static void insert(Bhuser bhUser) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		System.out.println("DbBullhorn: begin transaction");
		try {
			trans.begin();
			em.persist(bhUser);
			System.out.println("DbBullhorn: commit transaction");
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DbBullhorn: rollback transaction");
			trans.rollback();
		} finally {
			System.out.println("DbBullhorn: close em");
			em.close();
		}
	}
	public static String getGravatarURL(String email, Integer size){
		String url = "http://www.gravatar.com/avatar/" +
				MD5Util.md5Hex(email) + "?s=" + size.toString();
		return url;
	}
	public static void update(Bhuser bhUser) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.merge(bhUser);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	public static void delete(Bhuser bhUser) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.remove(em.merge(bhUser));
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	public static Bhuser getUserByEmail(String email)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "Select u from Bhuser u "
				+ "where u.useremail = :useremail";
		TypedQuery q = em.createQuery(qString, Bhuser.class);
		q.setParameter("useremail", email);
		Bhuser user = null;
		try {
			user = (Bhuser) q.getSingleResult();
		}catch (NoResultException e){
			System.out.println(e);
		}finally{
			em.close();
		}
		return user;	
	}
	public static boolean isValidUser(Bhuser user)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "Select count(b.bhuserid) from Bhuser b "
			+ "where b.useremail = :useremail and b.userpassword = :userpass";
		TypedQuery q = em.createQuery(qString,Long.class);
		boolean result = false;
		q.setParameter("useremail", user.getUseremail());
		q.setParameter("userpass", user.getUserpassword());
		
		try{
			long CountOfUserId = (long) q.getSingleResult();
			if (CountOfUserId > 0)
			{
				result = true;
			}
		}catch (Exception e){
			
			result = false;
		}
		finally{
				em.close();		
		}	
		return result;		
	}	
}