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

        public DbSet<Location> Locations { get; set; }
        public DbSet<Profile> Profiles { get; set; }
        public DbSet<Pilgrimage> Pilgrimages { get; set; }
    }
}
