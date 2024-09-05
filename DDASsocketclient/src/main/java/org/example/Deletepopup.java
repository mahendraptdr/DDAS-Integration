package org.example;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class FDemo extends JFrame
            {
                FDemo(DDA dda)
                {
                    setTitle("Data Duplicate Alert Message..");
                    dda.DDAs();
                    add(dda);
                }
            }

            class DDA extends JPanel {
                ImageIcon img1, download1, open1, delete1;
                Image img, download, open, delete;
                JLabel l1, l2, l3, l4;
                JButton b1, b2, b3;
                Font f, f11, f111;
                String s;
                JButton exit;

               public void  DDAs() {
                    setLayout(null);
                    img1 = new ImageIcon("dd11.jpg");
                    img = img1.getImage();
                    f = new Font("", f.BOLD, 28);
                    f11 = new Font("", f11.BOLD, 18);
                    f111 = new Font("", f111.BOLD, 15);

                    open1 = new ImageIcon("open.jpg");
                    open = open1.getImage();
                    b1 = new JButton("Open");
                    add(b1);
                    b1.setSize(90, 25);
                    b1.setLocation(55, 360);
                    b1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String filePath = s;
                            System.out.println(filePath);//for open path
                            File file = new File(filePath);
                            if (file.exists()) {

                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, "Error opening file manager: " + ex.getMessage());
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "File not found!");
                            }
                        }
                    });


                    delete1 = new ImageIcon("delete.jpg");
                    delete = delete1.getImage();
                    b2 = new JButton("Delete");
                    add(b2);
                    b2.setSize(90, 25);
                    b2.setLocation(208, 360);
                    b2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String filePath1 = s; // for delete path
                            File file1 = new File(filePath1);

                            if (file1.exists()) {
                                if (file1.delete()) {

                                    JOptionPane.showMessageDialog(null, "File deleted successfully!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to delete file!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "File not found!");

                            }
                        }
                    });

                    download1 = new ImageIcon("download.png");
                    download = download1.getImage();
                    b3 = new JButton("Download");
                    add(b3);
                    b3.setSize(90, 25);
                    b3.setLocation(360, 360);


                    l1 = new JLabel("File Duplicate Alert!!");
                    add(l1);
                    l1.setForeground(Color.RED);
                    l1.setSize(300, 40);
                    l1.setLocation(120, 50);
                    l1.setFont(f);

                    l2 = new JLabel("Duplicate File are Found!!");
                    add(l2);
                    l2.setForeground(Color.BLUE);
                    l2.setSize(300, 40);
                    l2.setLocation(120, 100);
                    l2.setFont(f11);

                    l3 = new JLabel("Do You Want to Delete ???");
                    add(l3);
                    //l3.setForeground(Color.RED);
                    l3.setSize(300, 40);
                    l3.setLocation(120, 120);
                    l3.setFont(f11);

                    l4 = new JLabel("PATH : " + s);
                    add(l4);
                    //l3.setForeground(Color.RED);
                    l4.setSize(400, 40);
                    l4.setLocation(50, 200);
                    l4.setFont(f111);


                    String userName = System.getProperty("user.name");
                    JLabel username = new JLabel("UserName : " + userName);
                    username.setBounds(380, 5, 150, 30);
                    add(username);

                    exit = new JButton("Exit");
                    exit.setSize(80, 25);
                    exit.setLocation(400, 435);
                    add(exit);
                    exit.addActionListener((e) -> {
                        System.exit(0);
                    });


                }

                public void paintComponent(Graphics g) {
                    g.drawImage(img, 0, 0, this);
                    g.drawImage(open, 50, 250, this);
                    g.drawImage(delete, 200, 250, this);
                    g.drawImage(download, 350, 250, this);
                }

                public void setPath(JSONObject object) throws JSONException {
                    s = object.getString("filePath");
                    System.out.println(s);
                }

                public void showPopup(DDA a) throws JSONException {

                        FDemo f1 = new FDemo(a);
                        f1.setVisible(true);
                        f1.setResizable(false);
                        f1.setSize(500, 500);
                        f1.setBackground(Color.WHITE);
                        f1.setLocationRelativeTo(null);
                        f1.setDefaultCloseOperation(f1.DISPOSE_ON_CLOSE);
                    }


                }


