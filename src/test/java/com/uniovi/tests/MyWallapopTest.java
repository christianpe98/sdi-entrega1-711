package com.uniovi.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
//Ordenamos las pruebas por el nombre del método

import com.uniovi.tests.pageobjects.PO_API;
import com.uniovi.tests.pageobjects.PO_AddOfferView;
import com.uniovi.tests.pageobjects.PO_ChatAPI;
import com.uniovi.tests.pageobjects.PO_ConversacionesAPI;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginAPI;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_MyOffers;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_OffersAPI;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_Purchased;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_RemoveUsersView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.pageobjects.PO_ViewOffers;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyWallapopTest {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox64 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver022 = "C:\\Users\\Christian\\Desktop\\Labs\\SDI\\Selenium\\PL-SDI-Sesión5-material\\PL-SDI-Sesion5-material\\geckodriver022win64.exe";
	// En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas):
	// static String PathFirefox65 =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox64, Geckdriver022);

	static String URLlocal = "https://localhost:7081";
	static String URLremota = "https://urlsdispring:xxxx";
	static String URL = URLlocal; // Se va a probar con la URL remota, sino URL=URLlocal

	private static final String URL_CHAT="https://localhost:7081/cliente.html";
	
	private List<String> emailsUsuarios=new ArrayList<String>();
	
	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		initdb();
		driver.navigate().to(URL);
	}

	@SuppressWarnings("serial")
	public void initdb() {
		driver.navigate().to(URL+"/reiniciarBBDD");
		reiniciarEmails();
	}

	private void reiniciarEmails() {
		emailsUsuarios.add("admin@email.com");
		emailsUsuarios.add("christian@email.com");
		emailsUsuarios.add("cristina@email.com");
		emailsUsuarios.add("noe@email.com");
		emailsUsuarios.add("enrique@email.com");
		emailsUsuarios.add("gema@email.com");
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		//driver.navigate().to(URL+"/reiniciarBBDD");
		driver.quit();
	}

	// PR1_1:Registro de Usuario con datos válidos.
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickElementId(driver, "registrarse");
		
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "josefo@email.com", "Josefo", "Perez", "77777", "77777");
		
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "id", "h2NombreApellido");
	}

	// PR1_2:Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos).
	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickElementId(driver, "registrarse");
		
		// Rellenamos el formulario con email vacío
		PO_RegisterView.fillForm(driver, " ", "Josefo", "Perez", "77777", "77777");
		// COmprobamos que seguimos en el registro
		PO_RegisterView.checkElement(driver, "id", "h2Registro");
		
		// Rellenamos el formulario con nombre vacío
		PO_RegisterView.fillForm(driver, "josefo@email.com", " ", "Perez", "77777", "77777");
		// COmprobamos que seguimos en el registro
		PO_RegisterView.checkElement(driver, "id", "alert");
		
		// Rellenamos el formulario con apellidos vacío
		PO_RegisterView.fillForm(driver, "josefo@email.com", "Josefo", " ", "77777", "77777");
		// COmprobamos que seguimos en el registro
		PO_RegisterView.checkElement(driver, "id", "alert");
		
	}
	
	// Registro de Usuario con datos inválidos (repetición de contraseña inválida)
		@Test
		public void PR03() {
			// Vamos al formulario de registro
			PO_HomeView.clickElementId(driver, "registrarse");
			
			// Rellenamos el formulario con contraseñas que no coinciden
			PO_RegisterView.fillForm(driver, "josefo@email.com", "Josefo", "Perez", "123456", "654321");
			// COmprobamos el error de las 2 contraseñas diferentes
			PO_RegisterView.checkElement(driver, "id", "alert");

		}

		//Registro de Usuario con datos inválidos (email existente).
		@Test
		public void PR04() {
			// Vamos al formulario de registro
			PO_HomeView.clickElementId(driver, "registrarse");
			
			// Rellenamos el formulario 
			PO_RegisterView.fillForm(driver, "christian@email.com", "Josefo", "Perez", "123456", "123456");
			
			// COmprobamos el error de las 2 contraseñas diferentes
			PO_RegisterView.checkElement(driver, "id", "alert");
		}
		
		//Inicio de sesión con datos válidos (administrador)
		@Test
		public void PR99() {
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "admin@email.com","admin");
			
			//Comprobamos que hemos entrado
			PO_View.checkElement(driver, "id", "h2NombreApellido");
		}
		
		//Inicio de sesión con datos válidos (usuario estándar).
		@Test
		public void PR98() {
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "christian@email.com","123456");
			
			//Comprobamos que hemos entrado
			PO_View.checkElement(driver, "id", "h2NombreApellido");
		}
		
		//Inicio de sesión con datos válidos (campo email o contraseña vacíos).
			@Test
			public void PR06() {
				// Vamos al formulario de inicio de sesion
				PO_HomeView.clickElementId(driver, "identificarse");
				
				// Rellenamos el formulario 
				PO_LoginView.fillForm(driver, " ","123456");
				
				//Comprobamos que hemos entrado
				PO_RegisterView.checkElement(driver, "id", "h2Indentificar");
			}
				
		//Inicio de sesión con datos inválidos (email existente, pero contraseña incorrecta)
		@Test
		public void PR05() {
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "christian@email.com","admin");
			
			//Comprobamos que hemos entrado
			PO_RegisterView.checkElement(driver, "id", "alert");
		}
		
		//Inicio de sesión con datos inválidos (usuario estándar, email no existente en la aplicación).
		@Test
		public void PR07() {
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "pepe@email.com","admin");
			
			//Comprobamos que hemos entrado
			PO_RegisterView.checkElement(driver, "id", "alert");
		}
		
		//Hacer click en la opción de salir de sesión y comprobar que se redirige a la página de inicio
		//de sesión (Login)
		@Test
		public void PR08()
		{
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "christian@email.com","123456");
			
			// Pinchamos en la opción del menú del perfil del usuario
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'profile-menu')]/a");
			elementos.get(0).click();
			
			//Pinchamos en el botón desconectar
			elementos = PO_View.checkElement(driver, "id", "btn_logout");
			elementos.get(0).click();
			
			//Comprobamos que estamos en la página de login
			PO_View.checkElement(driver, "id", "h2Indentificar");
		}
		
		//Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
		@Test
		public void PR09(){
			assertFalse(PO_View.elementExists(driver, "perfil-menu"));
			assertFalse(PO_View.elementExists(driver, "btn_logout"));
		}
		
		// Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema. 
		@Test
		public void PR10(){
			//Conseguir usuarios de la base de datos
			List<String> emailsBBDD=new ArrayList<>();
			
			
			//Entramos como administrador
			PO_HomeView.clickElementId(driver, "identificarse");
			PO_LoginView.fillForm(driver, "admin@email.com","admin");
			
			//Seleccionamos la gestión de usuarios
			PO_HomeView.clickElementId(driver, "users-menu");
			PO_HomeView.clickElementId(driver, "btn_listUser");
			
			//conseguimos las columnas con las listas de usuarios
			List<WebElement> elementos=PO_View.checkElement(driver, "@name", "email_value");
			
			List<String> emails=new ArrayList<String>();
			
			//obtenemos los emails
			for(int i=0;i<elementos.size();i++)
			{
				emails.add(elementos.get(i).getText());
			}
			
			for(String email:emails)
			{
				emailsUsuarios.contains(email);
			}
			
			
			//hay igual numero de dnis y usuarios
			assertEquals(emailsUsuarios.size(), emails.size());
		}
		
		// Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza
		//y dicho usuario desaparece
		@Test
		public void PR11(){			
			//Entramos como administrador
			PO_HomeView.clickElementId(driver, "identificarse");
			PO_LoginView.fillForm(driver, "admin@email.com","admin");
			
			//Seleccionamos la gestión de usuarios
			PO_HomeView.clickElementId(driver, "users-menu");
			PO_HomeView.clickElementId(driver, "btn_deleteUser");
					
			int numUsersView=PO_RemoveUsersView.numUsers(driver);
			
			//Borramos la primera posición
			PO_RemoveUsersView.clickCheckUser(driver, 0);
			PO_RemoveUsersView.clickBtnRemove(driver);
			
			assertTrue(numUsersView-1==PO_RemoveUsersView.numUsers(driver));
		}
		
		// Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza
		//y dicho usuario desaparece
			@Test
				public void PR12(){
				//Entramos como administrador
				PO_HomeView.clickElementId(driver, "identificarse");
				PO_LoginView.fillForm(driver, "admin@email.com","admin");
				
				//Seleccionamos la gestión de usuarios
				PO_HomeView.clickElementId(driver, "users-menu");
				PO_HomeView.clickElementId(driver, "btn_deleteUser");
						
				int numUsersView=PO_RemoveUsersView.numUsers(driver);
				
				//Borramos la última posición
				PO_RemoveUsersView.clickCheckUser(driver,numUsersView-1 );
				PO_RemoveUsersView.clickBtnRemove(driver);
				
				assertTrue(numUsersView-1==PO_RemoveUsersView.numUsers(driver));
				}
				
				// Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos
				//usuarios desaparecen
						@Test
						public void PR13(){
							//Entramos como administrador
							PO_HomeView.clickElementId(driver, "identificarse");
							PO_LoginView.fillForm(driver, "admin@email.com","admin");
							
							//Seleccionamos la gestión de usuarios
							PO_HomeView.clickElementId(driver, "users-menu");
							PO_HomeView.clickElementId(driver, "btn_deleteUser");
									
							int numUsersView=PO_RemoveUsersView.numUsers(driver);
							
							//Borramos los 3 primeros
							PO_RemoveUsersView.clickCheckUser(driver,0 );
							PO_RemoveUsersView.clickCheckUser(driver,1 );
							PO_RemoveUsersView.clickCheckUser(driver,2 );
							PO_RemoveUsersView.clickBtnRemove(driver);
							
							assertTrue(numUsersView-3==PO_RemoveUsersView.numUsers(driver));
						}
						
				// Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar el botón Submit.
				//Comprobar que la oferta sale en el listado de ofertas de dicho usuario
				@Test
				public void PR14(){
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Vamos a la vista para añadir una oferta
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_addOffer");
					
					PO_AddOfferView.addOffer(driver, "Botella", "Botella de agua", 500.17);				
					
					List<String> ofertas=PO_MyOffers.listMyOffersTitle(driver);
					
					assertEquals(5, ofertas.size());
					assertEquals("Botella", ofertas.get(4));

				}

				//Ir al formulario de alta de oferta, rellenarla con datos inválidos (campo título vacío) y pulsar
				//el botón Submit. Comprobar que se muestra el mensaje de campo obligatorio.
				@Test
				public void PR15(){
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Vamos a la vista para añadir una oferta
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_addOffer");
					
					PO_AddOfferView.addOffer(driver, " ", "Botella de agua", 500.17);
					
					PO_HomeView.checkElement(driver, "id", "alert");
				}
				
				//Mostrar el listado de ofertas para dicho usuario y comprobar que se muestran todas los que
				//existen para este usuario
				@Test
				public void PR16(){
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "personal-menu");
					PO_HomeView.clickElementId(driver, "btn_myOffers");
					
					int numOfertas=PO_MyOffers.numOffers(driver);
						
					
					assertEquals(4,numOfertas);
				}
				
				//Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar que la lista se actualiza y
				//que la oferta desaparece
				@Test
				public void PR17()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "personal-menu");
					PO_HomeView.clickElementId(driver, "btn_myOffers");
					
					int numOfertasAntes=PO_MyOffers.numOffers(driver);
					PO_MyOffers.removeOffer(driver, 0);
					
					//Obtenemos las ofertas que se ven en la vista
					int numOfertasDespués=PO_MyOffers.numOffers(driver);
					
					
					assertEquals(numOfertasAntes-1,numOfertasDespués);
				}
				
				//Ir a la lista de ofertas, borrar la última oferta de la lista, comprobar que la lista se actualiza y
				//que la oferta desaparece
				@Test
				public void PR18()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "personal-menu");
					PO_HomeView.clickElementId(driver, "btn_myOffers");
					
					int numOfertasAntes=PO_MyOffers.numOffers(driver);
					PO_MyOffers.removeOffer(driver, numOfertasAntes-1);
					
					//Obtenemos las ofertas que se ven en la vista
					int numOfertasDespués=PO_MyOffers.numOffers(driver);
					
					
					assertEquals(numOfertasAntes-1,numOfertasDespués);
				}
				
				//Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
				//corresponde con el listado de las ofertas existentes en el sistema
				@Test
				public void PR19()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_viewOffers");
					
					
					PO_ViewOffers.search(driver, "");
					List<String> nombreOfertas=PO_ViewOffers.listNameOffers(driver);
					
					assertEquals(20,nombreOfertas.size());
					
				}
				
				//Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
				//muestra la página que corresponde, con la lista de ofertas vacía.
				@Test
				public void PR20()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_viewOffers");
					
					
					PO_ViewOffers.search(driver, "QWEERTYGSFVS");
					List<String> nombreOfertas=PO_ViewOffers.listNameOffers(driver);
					
					assertEquals(0,nombreOfertas.size());
										
				}
				
				@Test
				public void PR21()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_viewOffers");
					
					
					PO_ViewOffers.search(driver, "CH");
					List<String> nombreOfertas=PO_ViewOffers.listNameOffers(driver);
					
					assertEquals(4,nombreOfertas.size());
					
					PO_ViewOffers.search(driver, "ch");
					nombreOfertas=PO_ViewOffers.listNameOffers(driver);
					
					assertEquals(4,nombreOfertas.size());
				}
				
				/*
				 *  Sobre una búsqueda determinada (a elección de desarrollador), comprar una oferta que deja
un saldo positivo en el contador del comprobador. Y comprobar que el contador se actualiza
correctamente en la vista del comprador.
				 */
				@Test
				public void PR22()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_viewOffers");

					PO_ViewOffers.purchaseOffer(driver, "Oferta G2");

					// Comprobamos el saldo
					double saldo=PO_HomeView.getSaldo(driver);
					assertEquals(24.5, saldo,0.1); // 100€-75.5€
				}
				
				/*
				 *  Sobre una búsqueda determinada (a elección de desarrollador), comprar una oferta que deja
un saldo 0 en el contador del comprobador. Y comprobar que el contador se actualiza correctamente en
la vista del comprador. 
				 */
				@Test
				public void PR23()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_viewOffers");

					PO_ViewOffers.purchaseOffer(driver, "Oferta E4");

					// Comprobamos el saldo
					double saldo=PO_HomeView.getSaldo(driver);
					assertEquals(0, saldo,0.1); // 100€-100€
				}
				
				/*
				 * Sobre una búsqueda determinada (a elección de desarrollador), intentar comprar una oferta
que esté por encima de saldo disponible del comprador. Y comprobar que se muestra el mensaje de
saldo no suficiente.
				 */
				@Test
				public void PR24()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "offers-menu");
					PO_HomeView.clickElementId(driver, "btn_viewOffers");

					PO_ViewOffers.purchaseOffer(driver, "Oferta E2");
					
					PO_HomeView.checkElement(driver, "id", "alert");
				}
				
				/*
				 * Ir a la opción de ofertas compradas del usuario y mostrar la lista. Comprobar que aparecen
las ofertas que deben aparecer.
				 */
				@Test
				public void PR25()
				{
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					PO_HomeView.clickElementId(driver, "personal-menu");
					PO_HomeView.clickElementId(driver, "btn_purchased");
					
					int compradas=PO_Purchased.numCompradas(driver);
					
					
					assertEquals(2,compradas);
				}
				
				/*Inicio de sesión con datos válidos.*/
				@Test
				public void PR29()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","123456");
					
					PO_HomeView.checkElement(driver,"id", "widget-ofertas");
				}
				
				/*
				 *  Inicio de sesión con datos inválidos (email existente, pero contraseña incorrecta).
				 */
				@Test
				public void PR30()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","7757357");
					
					PO_HomeView.checkElement(driver,"id", "alerta");
				}
				
				/*
				 *  Inicio de sesión con datos válidos (campo email o contraseña vacíos).
				 */
				@Test
				public void PR31()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "","");
					
					PO_HomeView.checkElement(driver,"id", "alerta");
				}
				
				/*
				 * Mostrar el listado de ofertas disponibles y comprobar que se muestran todas las que existen,
menos l				as del usuario identificado.
				 */
				@Test
				public void PR32()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","123456");
					
					PO_HomeView.checkElement(driver,"id", "widget-ofertas");
					assertEquals(20-4, PO_OffersAPI.numOffers(driver));
				}
				
				/*
				 * Sobre una búsqueda determinada de ofertas (a elección de desarrollador), enviar un mensaje a
					una oferta concreta. Se abriría dicha conversación por primera vez. Comprobar que el mensaje aparece
					en el listado de mensajes
				 */
				@Test
				public void PR33()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","123456");
					
					PO_OffersAPI.chatOffer(driver,1); // oferta CR2
					PO_ChatAPI.enviarMensaje(driver, "qwerty");
					PO_ChatAPI.contieneMensaje(driver, "qwerty");
					PO_API.desconectarse(driver);
					
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "cristina@email.com","123456");
					PO_OffersAPI.numOffers(driver);//La pagina cargue bien
					PO_API.ventanaConversaciones(driver);
					PO_ConversacionesAPI.abrirConversacion(driver,3); //conversacion Christian
					PO_ChatAPI.contieneMensaje(driver, "qwerty");
				}
				
				/*
				 *  Sobre el listado de conversaciones enviar un mensaje a una conversación ya abierta.
Comprobar que el mensaje aparece en el listado de mensajes.
				 */
				@Test
				public void PR34()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","123456");
					
					PO_OffersAPI.numOffers(driver);//La pagina cargue bien
					PO_API.ventanaConversaciones(driver);
					PO_ConversacionesAPI.abrirConversacion(driver,0);//CR1
					PO_ChatAPI.enviarMensaje(driver, "123");
					PO_ChatAPI.contieneMensaje(driver, "123");
					
					PO_API.desconectarse(driver);
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "cristina@email.com","123456");
					
					PO_OffersAPI.numOffers(driver);//La pagina cargue bien
					PO_API.ventanaConversaciones(driver);
					PO_ConversacionesAPI.abrirConversacion(driver,0);//CR1
					PO_ChatAPI.contieneMensaje(driver, "123");
					
				}
				
				/*
				 *  Mostrar el listado de conversaciones ya abiertas. Comprobar que el listado contiene las
conversaciones que deben ser.
				 */
				@Test
				public void PR35()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","123456");
					
					PO_OffersAPI.numOffers(driver);//La pagina cargue bien
					PO_API.ventanaConversaciones(driver);
					assertEquals(3, PO_ConversacionesAPI.numConversaciones(driver));
					
				}
				
				/*
				 * Sobre el listado de conversaciones ya abiertas. Pinchar el enlace Eliminar de la primera y
comprobar que el listado se actualiza correctamente.
				 */
				@Test
				public void PR36()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","123456");
					
					PO_OffersAPI.numOffers(driver);//La pagina cargue bien
					PO_API.ventanaConversaciones(driver);
					assertEquals(3, PO_ConversacionesAPI.numConversaciones(driver));
					PO_ConversacionesAPI.eliminarConversacion(driver, 0);
					PO_API.ventanaOfertas(driver); //hacer tiempo para que se elimine
					PO_API.ventanaConversaciones(driver);
					assertEquals(2, PO_ConversacionesAPI.numConversaciones(driver));
				}
				
				/*
				 * Sobre el listado de conversaciones ya abiertas. Pinchar el enlace Eliminar de la última y
					comprobar que el listado se actualiza correctamente.
				 */
				@Test
				public void PR37()
				{
					driver.navigate().to(URL_CHAT);
					//Entramos como usuario
					PO_HomeView.clickElementId(driver, "identificarse");
					PO_LoginAPI.fillForm(driver, "christian@email.com","123456");
					
					PO_OffersAPI.numOffers(driver);//La pagina cargue bien
					PO_API.ventanaConversaciones(driver);
					int numConversAntes=PO_ConversacionesAPI.numConversaciones(driver);
					PO_ConversacionesAPI.eliminarConversacion(driver,2);
					PO_API.ventanaOfertas(driver); //hacer tiempo para que se elimine
					PO_API.ventanaConversaciones(driver);
					assertEquals(numConversAntes-1, PO_ConversacionesAPI.numConversaciones(driver));
				}
}

