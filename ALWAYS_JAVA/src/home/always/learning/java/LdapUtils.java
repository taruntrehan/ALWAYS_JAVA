/**
 * 
 */
package home.always.learning.java;

import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * 
 */
public class LdapUtils {

	// required private variables
	private Properties properties;
	private DirContext dirContext;
	private SearchControls searchCtls;
	private String[] returnAttributes = { "sAMAccountName", "givenName",
			"mail", "memberOf", "displayname", "Useraccountcontrol" };
	private NamingEnumeration<SearchResult> queryRes;

	/*
	 * 
	 * Setting basic properties for connection setup.
	 * 
	 */
	public LdapUtils() {
		System.out.println("LdapUtils - Begin");
		try {
			properties = new Properties();
			properties.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			// This example leverages LDAP and not LDAPS and port connectivity is via 389
			properties.put(Context.PROVIDER_URL, "LDAP://"
					+ "<domain_address_here>:389/");
			properties.put(Context.SECURITY_AUTHENTICATION, "simple");

			// Setting basic properties for search control
			// These will be leveraged later for pulling some details from the ADS

			searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchCtls.setReturningAttributes(returnAttributes);

		} catch (Exception e) {
			System.out
			.println("Some Exception Occured in Initiating Ldap Conext Object-");
		}
	}

	/*
	 * In my use case; once the user is authenticated; i had to check for user's membership
	 * hence, i query ADS over LDAP and try to authorize user.
	 * Following method does the same.
	 */
	public boolean authorizeUser(String userName, String accessName) {
		boolean isAuthorized = false;
		System.out.println("authorizeUser(): Begin");
		try {
			String searchFilter = "(&(sAMAccountName=" + userName + "))";
			// initialize counter to total the results
			int totalResults = 0;
			// Search for objects using the filter
			// Example : DC=ads,DC=allzhere,DC=org
			// You can query your ADS admin for the domain string to setup search filter.
			queryRes = dirContext.search("DC=ads,DC=allzhere,DC=org", searchFilter,
					searchCtls);
			/*
			 *  Loop through the search results
			 *  Find the attribute value of "memberOf" and check if user is part of a particular group.
			 *  As per the returned value; set authrozed flag.
			 */
			
			while (queryRes.hasMoreElements()) {
				SearchResult srchResObj = (SearchResult) queryRes.next();
				totalResults++;
				// System.out.println("Search Object Name:" + srchResObj);
				Attributes attributesList = srchResObj.getAttributes();
				if (attributesList != null) {
					String userGroups = attributesList.get("memberof").get()
							.toString();
					System.out.println("User Groups String:" + userGroups);
					if (userGroups != null
							&& userGroups
							.indexOf("<ACCESS_CONTROL_GROUP_NAME>") != -1) {
						isAuthorized = true;
					}
				}
			}
			System.out.println("Search Query Returned " + totalResults);
		} catch (Exception e) {
			System.out
			.println("Generic Exception Occured in Checking Group Details -");
		}
		return isAuthorized;
	}

	/*
	 * 
	 * Method to authenticate a user.
	 * 
	 */

	public boolean authenticateUser(String domain, String userName,
			String password) {
		boolean isAuthenticated = false;
		System.out.println("authenticateUser(): Begin");
		try {

			/*
			 * The security principal setting is the tricky part.
			 * Some examples will suggest you to provide the CN name and other details in the following format
			 * env.put(Context.SECURITY_PRINCIPAL, "uid="+ userId +",ou=All Users,dc=site,dc=org"); 
			 * Somehow the above solution did not work for me.
			 * So, i connected with the ADS admin and asked the domain that is existing our organization.
			 * He suggested that its "abc" and simple suppy the security principal as 
			 * <domain_name>\\<user_name>
			 * The below line shows that only.
			 * Prior to this change, i tried multiple options but always got Ldap Error : 49 / 52e
			 * 
			 */

			properties
			.put(Context.SECURITY_PRINCIPAL, domain + "\\" + userName);
			properties.put(Context.SECURITY_CREDENTIALS, password);
			// System.out.println("Properties To Initiate Connect Call:"+properties);
			dirContext = new InitialDirContext(properties);
			isAuthenticated = true;
			// The below exceptions are caught and throw signal that Authentication has failed.

		} catch (AuthenticationException a) {
			System.out
			.println("Authentication Exception Occured in Initiating Ldap Conext Object-");
		} catch (Exception e) {
			System.out
			.println("Generic Exception Occured in Initiating Ldap Conext Object-");
		}
		return isAuthenticated;
	}

	/*
	 * Clean up code for LDAP connections.
	 * 
	 */

	public void cleanUp() {
		System.out.println("cleanUp(): Begin");
		try {
			if (queryRes != null) {
				queryRes.close();
			}
			if (dirContext != null) {
				dirContext.close();
			}
		} catch (Exception e) {
			System.out
			.println("Generic Exception Occured in Closing Ldap Conext Object-");
		}
	}

	public static void main(String[] args) {
		LdapUtils ldapUtilObj = new LdapUtils();
		boolean isAuthenticated = ldapUtilObj.authenticateUser(
				"<ADS_DIRECTORY_NAME>", "<user_name>", "<password_here>");
		System.out.println("User Authenticated Status:" + isAuthenticated);
		boolean isAuthorized = ldapUtilObj.authorizeUser("<user_name",
				"<ACCESS_CONTROL_GROUP_NAME>");
		System.out.println("User Authorized Status:" + isAuthorized);
		ldapUtilObj.cleanUp();
		ldapUtilObj = null;
	}
}
