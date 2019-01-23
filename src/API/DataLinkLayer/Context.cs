using DataLinkLayer.Models;
using Microsoft.EntityFrameworkCore;
using System;

namespace DataLinkLayer
{
    public class Context: DbContext
    {
        public Context(DbContextOptions<Context> options) : base(options)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelbuilder)
        {
            modelbuilder.Entity<CollectionLocation>().HasKey(t => new { t.CollectionID, t.LocationID });
            modelbuilder.Entity<CollectionLocation>().HasOne(c => c.Collection)
                                                     .WithMany(cl => cl.CollectionLocations)
                                                     .HasForeignKey(pt => pt.CollectionID);
            modelbuilder.Entity<CollectionLocation>().HasOne(l => l.Location)
                                                     .WithMany(lc => lc.collectionLocations)
                                                     .HasForeignKey(pt => pt.LocationID);



        }

        public DbSet<Location> Locations { get; set; }
        public DbSet<Profile> Profiles { get; set; }
        public DbSet<Pilgrimage> Pilgrimages { get; set; }
        public DbSet<Collection> Collections { get; set; }
        public DbSet<CollectionLocation> CollectionLocation { get; set; }
    }
}
