package servlets;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dtos.PurchaseOrderDTO;
import dtos.PurchaseOrderLineItemDTO;
//import dtos.VendorDTO;
import dtos.ProductDTO;
import dtos.ProductEJBDTO;
import dtos.PurchaseOrderEJBDTO;
import dtos.PurchaseOrderLineItemEJBDTO;
import dtos.VendorEJBDTO;
import ejbs.ProductFacadeBean;
import ejbs.PurchaseOrderFacadeBean;
import ejbs.VendorFacadeBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import models.PurchaseOrderModel;
import models.ProductModel;

/**
 *
 * @author elauersen
 */
@WebServlet(name = "PurchaseOrderPDFServlet", urlPatterns = {"/PurchaseOrderPDF"})
public class PurchaseOrderPDFServlet extends HttpServlet {
    // resource already defined in Glassfish

    //@Resource(lookup = "jdbc/Info5059db")
    //DataSource ds;
    //PurchaseOrderModel erm = new PurchaseOrderModel();
    //VendorModel em = new VendorModel();
    //ProductModel exp = new ProductModel();
    @EJB
    private VendorFacadeBean vfb;

    @EJB
    private ProductFacadeBean pfb;

    @EJB
    private PurchaseOrderFacadeBean pofb;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("purchaseorderid"));
            PurchaseOrderEJBDTO reportDTO = pofb.get(id);
            buildpdf(reportDTO, response);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private void buildpdf(PurchaseOrderEJBDTO reportDTO, HttpServletResponse response) {
        Font catFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
        Font subFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        NumberFormat numFormatter = NumberFormat.getIntegerInstance(Locale.US);
        String IMG = getServletContext().getRealPath("/img/mordenlogo.png");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        ProductModel model = new ProductModel();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            Paragraph preface = new Paragraph();
            // add the logo here
            Image image1 = Image.getInstance(IMG);
            image1.setAbsolutePosition(55f, 750f);
            preface.add(image1);
            preface.setAlignment(Element.ALIGN_RIGHT);
            // Lets write a big header
            Paragraph mainHead = new Paragraph(String.format("%55s", "Purchase Order REPORT"), catFont);
            preface.add(mainHead);
            preface.add(new Paragraph(String.format("%90s", "PO#:" + reportDTO.getId()),
                    subFont));
            addEmptyLine(preface, 3);
            // add the employee info for the order here
            PdfPTable vendorTable = new PdfPTable(2);
            vendorTable.setWidthPercentage(35);
            vendorTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            VendorEJBDTO vendorDTO = vfb.getOne(reportDTO.getVendorid());
            PdfPCell c1 = new PdfPCell(new Paragraph("Vendor:", smallBold));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBorder(0);
            vendorTable.addCell(c1);
            c1 = new PdfPCell(new Phrase(vendorDTO.getName()));
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setBorder(0);
            vendorTable.addCell(c1);
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            vendorTable.addCell(c1);
            c1 = new PdfPCell(new Phrase(vendorDTO.getAddress1()));
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            vendorTable.addCell(c1);
            c1.setBorder(0);
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            vendorTable.addCell(c1);
            c1 = new PdfPCell(new Phrase(vendorDTO.getCity()));
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            vendorTable.addCell(c1);
            c1.setBorder(0);
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            vendorTable.addCell(c1);
            c1 = new PdfPCell(new Phrase(vendorDTO.getProvince()));
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            vendorTable.addCell(c1);
            c1.setBorder(0);
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            vendorTable.addCell(c1);
            c1 = new PdfPCell(new Phrase(vendorDTO.getPostalcode()));
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            vendorTable.addCell(c1);
            c1.setBorder(0);
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            vendorTable.addCell(c1);
            preface.setAlignment(Element.ALIGN_LEFT);
            preface.add(vendorTable);
            addEmptyLine(preface, 1);
            // set up the order details in a table
            PdfPTable table = new PdfPTable(5);
            c1 = new PdfPCell(new Phrase("Product Code"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Product Description"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Quantity Sold"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            c1 = new PdfPCell(new Phrase("Price"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            c1 = new PdfPCell(new Phrase("Ext Price"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            table.setHeaderRows(1);
            double tot = 0.0;
            double price = 0.0;
            // dump out the line items
            for (PurchaseOrderLineItemEJBDTO line : reportDTO.getItems()) {
                ProductEJBDTO product = pfb.getOne(line.getProductid());
                c1 = new PdfPCell(new Phrase(line.getProductid()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(product.getName()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                String qty = Integer.toString(line.getQty());
                c1 = new PdfPCell(new Phrase(qty));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(formatter.format(product.getCostprice())));
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(c1);
                double ext = (line.getPrice().doubleValue() * line.getQty());   
                //double ext = 5;//DEFINITELY CHANGE THIS LOL
                BigDecimal x = product.getCostprice();
                //double ext = x;
                //double ext = x * line.getQty();
                c1 = new PdfPCell(new Phrase(formatter.format(ext)));
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(c1);
                tot += ext;
            }
            // report total
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            c1.setColspan(3);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Sub Total:"));
            //c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(formatter.format(tot)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            c1.setColspan(3);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Tax:"));
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(formatter.format(tot * 0.13)));

            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(""));
            c1.setBorder(0);
            c1.setColspan(3);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Order Totals:"));
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(formatter.format(tot * 1.13)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(c1);
            preface.add(table);
            addEmptyLine(preface, 3);
            // footer for date
            preface.setAlignment(Element.ALIGN_CENTER);
            preface.add(new Paragraph(String.format("%85s", "PO Generated on: "
                    + reportDTO.getDate(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    smallBold)));
            document.add(preface);
            document.close();
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Transfer-Encoding", "binary");
            //response.setHeader("Content-Disposition", "inline; filename=\"po.PDF\"");
            response.setHeader("Content-Disposition", "attachment; filename=PO"
                    + reportDTO.getId() + ".pdf");
            // setting the content type
            response.setContentType("application/pdf");
            //response.setContentType("application/x-download");
            // the contentlength
            response.setContentLength(baos.size());
            try ( // write ByteArrayOutputStream to the ServletOutputStream
                    OutputStream os = response.getOutputStream()) {
                baos.writeTo(os);
                os.flush();
            }
        } catch (NullPointerException npe) {
            try {
                PrintWriter out;
                out = response.getWriter();
                out.println("Error Getting Picture for PO on Server");
            } catch (IOException ex) {
                Logger.getLogger(PurchaseOrderPDFServlet.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign onthe left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
