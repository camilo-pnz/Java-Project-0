package uts.edu.java.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uts.edu.java.proyecto.modelo.DetalleVenta;
import uts.edu.java.proyecto.modelo.Producto;
import uts.edu.java.proyecto.modelo.Venta;
import uts.edu.java.proyecto.repositorio.ProductoRepositorio;
import uts.edu.java.proyecto.repositorio.VentaRepositorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaServicio implements IVentaServicio {

    @Autowired
    private VentaRepositorio ventaRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Venta> listar() {
        return ventaRepositorio.findAll();
    }

    @Override
    public Venta listarId(Integer id) {
        return ventaRepositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Venta save(Venta venta, List<DetalleVenta> detalles) {
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado(Venta.Estado.COMPLETADA);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            Producto producto = productoRepositorio.findById(
                detalle.getProducto().getIdProducto()).orElseThrow();
            detalle.setPrecioUnitario(producto.getPrecioVenta());
            BigDecimal subDetalle = producto.getPrecioVenta()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(subDetalle);
            subtotal = subtotal.add(subDetalle);

            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepositorio.save(producto);
            detalle.setVenta(venta);
        }

        venta.setSubtotal(subtotal);
        venta.setImpuesto(BigDecimal.ZERO);
        venta.setTotal(subtotal);
        venta.setDetalles(detalles);

        return ventaRepositorio.save(venta);
    }

    @Override
    @Transactional
    public void anular(Integer id) {
        Venta venta = ventaRepositorio.findById(id).orElseThrow();

        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoRepositorio.findById(
                detalle.getProducto().getIdProducto()).orElseThrow();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepositorio.save(producto);
        }

        venta.setEstado(Venta.Estado.ANULADA);
        ventaRepositorio.save(venta);
    }
}