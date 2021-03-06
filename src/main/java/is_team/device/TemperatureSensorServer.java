package is_team.device;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.model.meta.*;

import is_team.device.Device;
import is_team.service.*;

public class TemperatureSensorServer implements Runnable {
  public static void main(String[] args) {
    // Start a user thread that runs the UPnP stack
    Thread serverThread = new Thread(new TemperatureSensorServer());
    serverThread.setDaemon(false);
    serverThread.start();
  }

  public void run() {
    try {
      final UpnpService upnpService = new UpnpServiceImpl();

      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          upnpService.shutdown();
        }
      });

      Device device = new Device("Temperature Sensor", "TemperatureSensor", "Temperature Sensor",
        "icon/temperature_sensor_icon.png", ChangeTemperature.class.getName());
      LocalDevice localDevice = device.getDevice();
      upnpService.getRegistry().addDevice(localDevice);
    } catch (Exception ex) {
      System.err.println("Exception occured: " + ex);
      ex.printStackTrace(System.err);
      System.exit(1);
    }
  }
}
