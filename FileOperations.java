import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.Arrays;

public class FileOperations extends JFrame implements ActionListener {
    private JTextField filename1Field, filename2Field;
    private JTextArea outputArea;
    private JButton createButton, compareButton, copyButton, deleteButton, clearButton;

    public FileOperations() {
        setTitle("File Operations");
        setLayout(new GridLayout(4, 1));

        // Panel for file names input
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new GridLayout(2, 2));
        
        filePanel.add(new JLabel("Filename 1 :"));
        filename1Field = new JTextField();
        filePanel.add(filename1Field);
        
        filePanel.add(new JLabel("Filename 2 :"));
        filename2Field = new JTextField();
        filePanel.add(filename2Field);
        
        add(filePanel);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        
        createButton = new JButton("CREATE");
        createButton.addActionListener(this);
        buttonPanel.add(createButton);
        
        compareButton = new JButton("COMPARE");
        compareButton.addActionListener(this);
        buttonPanel.add(compareButton);
        
        copyButton = new JButton("COPY");
        copyButton.addActionListener(this);
        buttonPanel.add(copyButton);
        
        deleteButton = new JButton("DELETE");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        
        clearButton = new JButton("CLEAR");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);
        
        add(buttonPanel);

        // Panel for output
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(outputPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String filename1 = filename1Field.getText();
        String filename2 = filename2Field.getText();
        outputArea.setText("");

        try {
            if (e.getSource() == createButton) {
                if (!filename1.isEmpty()) {
                    File file = new File(filename1);
                    if (file.createNewFile()) {
                        outputArea.setText("File created: " + filename1);
                    } else {
                        outputArea.setText("File already exists: " + filename1);
                    }
                }
            } else if (e.getSource() == compareButton) {
                if (!filename1.isEmpty() && !filename2.isEmpty()) {
                    Path path1 = Paths.get(filename1);
                    Path path2 = Paths.get(filename2);
                    if (Files.exists(path1) && Files.exists(path2)) {
                        byte[] file1 = Files.readAllBytes(path1);
                        byte[] file2 = Files.readAllBytes(path2);
                        if (Arrays.equals(file1, file2)) {
                            outputArea.setText("Files are identical.");
                        } else {
                            outputArea.setText("Files are different.");
                        }
                    } else {
                        outputArea.setText("One or both files do not exist.");
                    }
                }
            } else if (e.getSource() == copyButton) {
                if (!filename1.isEmpty() && !filename2.isEmpty()) {
                    Files.copy(Paths.get(filename1), Paths.get(filename2), StandardCopyOption.REPLACE_EXISTING);
                    outputArea.setText("File copied from " + filename1 + " to " + filename2);
                }
            } else if (e.getSource() == deleteButton) {
                if (!filename1.isEmpty()) {
                    Files.deleteIfExists(Paths.get(filename1));
                    outputArea.setText("File deleted: " + filename1);
                }
            } else if (e.getSource() == clearButton) {
                filename1Field.setText("");
                filename2Field.setText("");
                outputArea.setText("");
            }
        } catch (IOException ex) {
            outputArea.setText("An error occurred: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new FileOperations();
    }
}