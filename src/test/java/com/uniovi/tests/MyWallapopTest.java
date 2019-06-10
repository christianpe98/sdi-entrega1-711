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

import com.uniovi.tests.pageobjects.PO_AddOfferView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_MyOffers;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_Properties;
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
		public void PR05() {
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "admin@email.com","admin");
			
			//Comprobamos que hemos entrado
			PO_View.checkElement(driver, "id", "h2NombreApellido");
		}
		
		//Inicio de sesión con datos válidos (usuario estándar).
		@Test
		public void PR06() {
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "christian@email.com","123456");
			
			//Comprobamos que hemos entrado
			PO_View.checkElement(driver, "id", "h2NombreApellido");
		}
		
		//Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos)
			@Test
			public void PR07() {
				// Vamos al formulario de inicio de sesion
				PO_HomeView.clickElementId(driver, "identificarse");
				
				// Rellenamos el formulario 
				PO_LoginView.fillForm(driver, " ","123456");
				
				//Comprobamos que hemos entrado
				PO_RegisterView.checkElement(driver, "id", "h2Indentificar");
			}
				
		//Inicio de sesión con datos inválidos (usuario estándar, email no existente en la aplicación).
		@Test
		public void PR08() {
			// Vamos al formulario de inicio de sesion
			PO_HomeView.clickElementId(driver, "identificarse");
			
			// Rellenamos el formulario 
			PO_LoginView.fillForm(driver, "christian@email.com","admin");
			
			//Comprobamos que hemos entrado
			PO_RegisterView.checkElement(driver, "id", "alert");
		}
		
		//Inicio de sesión con datos inválidos (usuario estándar, email no existente en la aplicación).
		@Test
		public void PR09() {
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
		public void PR10()
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
		public void PR11(){
			assertFalse(PO_View.elementExists(driver, "perfil-menu"));
			assertFalse(PO_View.elementExists(driver, "btn_logout"));
		}
		
		// Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema. 
		@Test
		public void PR12(){
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
		public void PR13(){			
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
				public void PR14(){
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
						public void PR15(){
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
				public void PR16(){
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
				public void PR17(){
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
				public void PR18(){
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
				public void PR19()
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
				public void PR20()
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
				public void PR21()
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
				public void PR22()
				{
					//Entramos como usuario
					PO_HomeView.clickOption(driver, "login",2, "class", "btn btn-primary");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
					elementos.get(0).click();
					
					//Pinchamos en el botón ver lista de ofertas disponibles
					elementos = PO_View.checkElement(driver, "id", "btn_viewOffers");
					elementos.get(0).click();
					
					PO_ViewOffers.search(driver, "dsjvgsojhfbsdojf");
					assertFalse(PO_ViewOffers.elementExistsName(driver, "title_value"));
										
				}
				
				@Test
				public void PR23()
				{
					//Entramos como usuario
					PO_HomeView.clickOption(driver, "login",2, "class", "btn btn-primary");
					PO_LoginView.fillForm(driver, "christian@email.com","123456");
					
					//Seleccionamos 
					List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
					elementos.get(0).click();
					
					//Pinchamos en el botón ver lista de ofertas disponibles
					elementos = PO_View.checkElement(driver, "id", "btn_viewOffers");
					elementos.get(0).click();
					
					PO_ViewOffers.purchaseOffer(driver, "B3");
					
					//Seleccionamos 
					elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'profile-menu')]/a");
					elementos.get(0).click();
					
					//Pinchamos en el botón ver lista de ofertas disponibles
					elementos = PO_View.checkElement(driver, "id", "btn_profile");
					elementos.get(0).click();
					
					elementos=PO_View.checkElement(driver, "id", "saldo_value");
					
					assertTrue(elementos.get(0).getText()=="0.0");
				}
				
}
