using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace API.Migrations
{
    public partial class collection : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "CollectionID",
                table: "Locations",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "Collections",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    FireBaseID = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Collections", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "CollectionLocation",
                columns: table => new
                {
                    CollectionID = table.Column<int>(nullable: false),
                    LocationID = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CollectionLocation", x => new { x.CollectionID, x.LocationID });
                    table.ForeignKey(
                        name: "FK_CollectionLocation_Collections_CollectionID",
                        column: x => x.CollectionID,
                        principalTable: "Collections",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_CollectionLocation_Locations_LocationID",
                        column: x => x.LocationID,
                        principalTable: "Locations",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Locations_CollectionID",
                table: "Locations",
                column: "CollectionID");

            migrationBuilder.CreateIndex(
                name: "IX_CollectionLocation_LocationID",
                table: "CollectionLocation",
                column: "LocationID");

            migrationBuilder.AddForeignKey(
                name: "FK_Locations_Collections_CollectionID",
                table: "Locations",
                column: "CollectionID",
                principalTable: "Collections",
                principalColumn: "ID",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Locations_Collections_CollectionID",
                table: "Locations");

            migrationBuilder.DropTable(
                name: "CollectionLocation");

            migrationBuilder.DropTable(
                name: "Collections");

            migrationBuilder.DropIndex(
                name: "IX_Locations_CollectionID",
                table: "Locations");

            migrationBuilder.DropColumn(
                name: "CollectionID",
                table: "Locations");
        }
    }
}
