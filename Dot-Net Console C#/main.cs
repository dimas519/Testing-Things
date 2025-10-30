using System;
using System.Data.SqlClient;
using System.Diagnostics;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("=== Demo Program ADO.NET + Perhitungan Berat ===");

        // Panggil method database
        GetDataFromDatabase();

        // Panggil method perhitungan
        HeavyCalculation();

        Console.WriteLine("Selesai. Tekan ENTER untuk keluar.");
        Console.ReadLine();
    }

    // Method untuk koneksi ke database MSSQL (Docker)
    static void GetDataFromDatabase()
    {
        // Connection string untuk SQL Server Docker
        string connectionString = "Server=127.0.0.1,1433;Database=master;User Id=sa;Password=P@ssw0rd123;TrustServerCertificate=True;";

        try
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                conn.Open();
                Console.WriteLine("Koneksi ke database berhasil!");

                // Query sederhana
                string query = "SELECT name FROM sys.databases";
                using (SqlCommand cmd = new SqlCommand(query, conn))
                using (SqlDataReader reader = cmd.ExecuteReader())
                {
                    Console.WriteLine("Daftar database:");
                    while (reader.Read())
                    {
                        Console.WriteLine(" - " + reader["name"]);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error koneksi database: " + ex.Message);
        }
    }

    // Method untuk simulasi perhitungan berat
    static void HeavyCalculation()
    {
        Console.WriteLine("HeavyCalculation");
        Stopwatch sw = Stopwatch.StartNew();

        long total = 0;
        for (int i = 0; i < 50000000; i++)
        {
            total += i % 7;
        }

        sw.Stop();
        Console.WriteLine("Hasil perhitungan: " + total);
        Console.WriteLine("Waktu yang dibutuhkan: " + sw.ElapsedMilliseconds + " ms");
    }


     // Method untuk simulasi perhitungan berat
    static void HeavyCalculation()
    {

        long total = 0;
        for (int i = 0; i < 50000000; i++)
        {
            total += i % 7;
        }


    }
}