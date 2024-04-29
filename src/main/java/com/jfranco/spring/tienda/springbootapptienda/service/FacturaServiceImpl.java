package com.jfranco.spring.tienda.springbootapptienda.service;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IFacturaDao;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;

@Service
public class FacturaServiceImpl implements IFacturaService{
    @Autowired
    private IFacturaDao facturaDao;
    
    @Override
    public List<Factura> findAll() {
        return (List<Factura>) facturaDao.findAll();
    }

    @Override
    public void save(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    public Factura findOne(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    public void Eliminar(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    public Map<String, Double> calcularGananciaMes() {
        List<Factura> facturas = (List<Factura>) facturaDao.findAll();
        Map<String, Double> gananciaMes = new TreeMap<>();

        for (Factura factura : facturas) {
            YearMonth yearMonth = convertirAFechaAnioMes(factura.getFechaCreacion());
            String fecha = yearMonth.toString();
            Double ganancia = factura.getTotal();

            double gananciaparaMes = gananciaMes.getOrDefault(fecha,0.0) ;
            gananciaparaMes += ganancia;

            gananciaMes.put(fecha, gananciaparaMes);

            
        }
            
        
        return gananciaMes;
    }

    public YearMonth convertirAFechaAnioMes(Date fecha) {
        // Convertir Date a Instant
        Instant instant = fecha.toInstant();
        
        // Convertir Instant a ZonedDateTime usando la zona horaria predeterminada
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        
        // Obtener el YearMonth de la fecha
        return YearMonth.from(zonedDateTime);
    }

    @Override
    public void deleteAll() {
       facturaDao.deleteAll();
    }
    
}
