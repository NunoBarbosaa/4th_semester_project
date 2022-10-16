package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ViewProductInfoController {

    public String productAllInformation(Product id) {
        return id.toStringDisplay();
    }

    public boolean displayPictures(Product id) throws InterruptedException {
        List<byte[]> images = id.getImages();
        if (images == null || images.isEmpty())
            return false;
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        int counter = 0;
        BufferedImage bf = null;
        for (int i = 0; i < images.size() ; i++) {
            try {
                InputStream inputStream = new ByteArrayInputStream(images.get(i));
                bf = ImageIO.read(inputStream);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            frame.getContentPane().add(new JLabel(new ImageIcon(bf)));
            frame.pack();
            frame.setVisible(true);
        }
        return true;
    }
}
